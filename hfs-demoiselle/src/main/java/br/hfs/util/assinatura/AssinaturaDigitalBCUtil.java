package br.hfs.util.assinatura;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.StoreException;

public class AssinaturaDigitalBCUtil  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("AssinaturaDigitalBCUtil");

	public byte[] assinarPKCS7_BouncyCastle(String algoritmo,
			InputStream nomeKeyStore, String aliasKeySore,
			String senhaKeyStore, byte[] conteudo) throws AssinaturaDigitalException {
		byte[] signedBytes = null;
		
		try {
			// Carregar a KeyStore
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(nomeKeyStore, senhaKeyStore.toCharArray());

			// Gerar assinatura
			Security.addProvider(new BouncyCastleProvider());

			Certificate[] certchain = (Certificate[]) keystore
					.getCertificateChain(aliasKeySore);

			final List<Certificate> certlist = new ArrayList<Certificate>();

			for (int i = 0, length = certchain == null ? 0 : certchain.length; i < length; i++) {
				certlist.add(certchain[i]);
			}

			Store<?> certstore = new JcaCertStore(certlist);

			Certificate cert = keystore.getCertificate(aliasKeySore);

			ContentSigner signer = new JcaContentSignerBuilder(algoritmo)
					.setProvider("BC").build(
							(PrivateKey) (keystore.getKey(aliasKeySore,
									senhaKeyStore.toCharArray())));

			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();

			generator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
					new JcaDigestCalculatorProviderBuilder().setProvider("BC")
							.build()).build(signer, (X509Certificate) cert));
			
			generator.addCertificates(certstore);

			// Assinar PKCS7
			CMSTypedData cmsdata = new CMSProcessableByteArray(conteudo);
			CMSSignedData signeddata = generator.generate(cmsdata, true);
			signedBytes = signeddata.getEncoded();
		} catch (CertificateEncodingException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (UnrecoverableKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (OperatorCreationException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (CMSException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}

		return signedBytes;
	}

	@SuppressWarnings("unchecked")
	public boolean verificarPKCS7_BouncyCastle(byte[] conteudoAssinado)
			throws AssinaturaDigitalException {
		boolean retorno = false;
		
		try {
			Security.addProvider(new BouncyCastleProvider());

			CMSSignedDataParser sp = new CMSSignedDataParser(
					new JcaDigestCalculatorProviderBuilder().setProvider("BC")
							.build(), conteudoAssinado);

			// CMSSignedDataParser ep = new CMSSignedDataParser(
			// new BufferedInputStream(encapSigData, encapSigData.length));

			sp.getSignedContent().drain();

			Store<?> certStore = sp.getCertificates();
			SignerInformationStore signers = sp.getSignerInfos();

			Collection<?> c = signers.getSigners();
			Iterator<?> it = c.iterator();
			
			while (it.hasNext()) {
				SignerInformation signer = (SignerInformation) it.next();
				Collection<?> certCollection = certStore
						.getMatches(signer.getSID());

				Iterator<?> certIt = certCollection.iterator();
				X509CertificateHolder cert = (X509CertificateHolder) certIt.next();

				retorno = signer.verify(new JcaSimpleSignerInfoVerifierBuilder()
						.setProvider("BC").build(cert));
				break;
			}
		} catch (OperatorCreationException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (StoreException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (CMSException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
		return retorno;
	}

}
