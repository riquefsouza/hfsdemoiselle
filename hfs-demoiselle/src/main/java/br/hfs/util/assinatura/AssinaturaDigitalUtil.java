package br.hfs.util.assinatura;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.logging.Logger;

import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;
import br.hfs.util.criptografia.CriptografiaUtil;

@SuppressWarnings("restriction")
public final class AssinaturaDigitalUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("AssinaturaDigitalUtil");

	private CriptografiaUtil criptografiaUtil;

	public AssinaturaDigitalUtil() {
		criptografiaUtil = new CriptografiaUtil();
	}

	public byte[] assinarPKCS7(boolean JKS_ou_PKCS12, InputStream nomeStore, String senhaStore,
			byte[] dataToSign) throws AssinaturaDigitalException {
		byte[] encodedPKCS7 = null;
				
		try {
			KeyStore clientStore;
			
			if (JKS_ou_PKCS12) {
				clientStore = KeyStore.getInstance("JKS");
			} else {
				clientStore = KeyStore.getInstance("PKCS12");
			}

			// replace testPass with the p12 password/pin
			clientStore.load(nomeStore, senhaStore.toCharArray());

			Enumeration<String> aliases = clientStore.aliases();
			String aliaz = "";
			while (aliases.hasMoreElements()) {
				aliaz = aliases.nextElement();
				if (clientStore.isKeyEntry(aliaz)) {
					break;
				}
			}
			X509Certificate c = (X509Certificate) clientStore.getCertificate(aliaz);

			Signature signature = Signature.getInstance("Sha1WithRSA");
			signature.initSign((PrivateKey) clientStore.getKey(aliaz,
					senhaStore.toCharArray()));
			signature.update(dataToSign);
			byte[] signedData = signature.sign();

			X500Name xName = X500Name.asX500Name(c.getSubjectX500Principal());
			BigInteger serial = c.getSerialNumber();
			AlgorithmId digestAlgorithmId = new AlgorithmId(AlgorithmId.SHA_oid);
			AlgorithmId signAlgorithmId = new AlgorithmId(
					AlgorithmId.RSAEncryption_oid);

			SignerInfo sInfo = new SignerInfo(xName, serial, digestAlgorithmId,
					signAlgorithmId, signedData);
			ContentInfo cInfo = new ContentInfo(ContentInfo.DIGESTED_DATA_OID,
					new DerValue(DerValue.tag_OctetString, dataToSign));

			PKCS7 p7 = new PKCS7(new AlgorithmId[] { digestAlgorithmId }, cInfo,
					new X509Certificate[] { c }, new SignerInfo[] { sInfo });

			ByteArrayOutputStream bOut = new DerOutputStream();
			p7.encodeSignedData(bOut);
			encodedPKCS7 = bOut.toByteArray();
		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (UnrecoverableKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}

		return encodedPKCS7;

		/*
		 * X509Certificate prevCert = null; X509Certificate[] certs =
		 * p7.getCertificates(); for (int i = 0; i < certs.length; i++) { if (c
		 * != null) { prevCert.verify(certs[i].getPublicKey()); } prevCert =
		 * certs[i]; }
		 */
	}

	public byte[] assinarViaToken(byte[] conteudo) throws AssinaturaDigitalException {
		byte[] signature = null;
		try {
			KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			ks.load(null, null); 

			String alias = "";
			Enumeration<?> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) {
				alias = (String) aliasEnum.nextElement();
			}		

			PrivateKey privKey = (PrivateKey) ks.getKey(alias, null);

			Provider p = ks.getProvider();
			Signature sig = Signature.getInstance("SHA1withRSA", p);
			sig.initSign(privKey);
			sig.update(conteudo);
			signature = sig.sign();
			
		} catch (UnrecoverableKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchProviderException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
		
		return signature;		
	}

	public boolean verificarViaToken(byte[] conteudo, byte[] signature) throws AssinaturaDigitalException {
		try {
			KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			ks.load(null, null); 

			String alias = "";
			Enumeration<?> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) {
				alias = (String) aliasEnum.nextElement();
			}
			
			Provider p = ks.getProvider();
			Signature sig = Signature.getInstance("SHA1withRSA", p);
			
			Certificate cert = ks.getCertificate(alias);
			sig.initVerify(cert);
			sig.update(conteudo);
			return sig.verify(signature);
			
		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchProviderException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
	}
	
	public byte[] assinarPKCS7_Token(byte[] dataToSign) throws AssinaturaDigitalException {
		byte[] encodedPKCS7 = null;
		
		try {
			KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			ks.load(null, null); 

			String alias = "";
			Enumeration<?> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) {
				alias = (String) aliasEnum.nextElement();
				if (ks.isKeyEntry(alias)) {
					break;
				}	
			}		

			PrivateKey privKey = (PrivateKey) ks.getKey(alias, null);
			
			//Certificate[] chain = ks.getCertificateChain(alias);			
			X509Certificate[] chainX509 = (X509Certificate[]) ks.getCertificateChain(alias);			
			X509Certificate c = (X509Certificate) ks.getCertificate(alias);

			Provider p = ks.getProvider();
			Signature signature = Signature.getInstance("Sha1WithRSA", p);
			signature.initSign(privKey);
			signature.update(dataToSign);
			byte[] signedData = signature.sign();

			X500Name xName = X500Name.asX500Name(c.getSubjectX500Principal());
			BigInteger serial = c.getSerialNumber();
			AlgorithmId digestAlgorithmId = new AlgorithmId(AlgorithmId.SHA_oid);
			AlgorithmId signAlgorithmId = new AlgorithmId(
					AlgorithmId.RSAEncryption_oid);

			SignerInfo sInfo = new SignerInfo(xName, serial, digestAlgorithmId,
					signAlgorithmId, signedData);
			ContentInfo cInfo = new ContentInfo(ContentInfo.DIGESTED_DATA_OID,
					new DerValue(DerValue.tag_OctetString, dataToSign));

			//PKCS7 p7 = new PKCS7(new AlgorithmId[] { digestAlgorithmId }, cInfo, new X509Certificate[] { c }, new SignerInfo[] { sInfo });
			PKCS7 p7 = new PKCS7(new AlgorithmId[] { digestAlgorithmId }, cInfo, chainX509 , new SignerInfo[] { sInfo });

			ByteArrayOutputStream bOut = new DerOutputStream();
			p7.encodeSignedData(bOut);
			encodedPKCS7 = bOut.toByteArray();
			
		} catch (UnrecoverableKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchProviderException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
		
		return encodedPKCS7;		
	}

	public byte[] criarAssinatura(PrivateKey key, byte[] buffer)
			throws AssinaturaDigitalException {
		try {
			Signature sig = Signature.getInstance(key.getAlgorithm());
			sig.initSign(key);
			sig.update(buffer, 0, buffer.length);
			return sig.sign();
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
	}

	public String[] listarAlgoritmosAssinatura() {
		return criptografiaUtil.getServicoCriptografia("Signature");
	}
/*
	public void assinarObjeto() throws AssinaturaDigitalException {
		PublicKey publicKey = null;
		PrivateKey privateKey = null;
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			keyGen.initialize(1024);
			KeyPair keypair = keyGen.genKeyPair();
			privateKey = keypair.getPrivate();
			publicKey = keypair.getPublic();
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}

		SignedObject so = null;
		try {
			Serializable o = new MyClass();
			Signature sig = Signature.getInstance(privateKey.getAlgorithm());
			so = new SignedObject(o, privateKey, sig);
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
		try {
			Signature sig = Signature.getInstance(publicKey.getAlgorithm());
			boolean b = so.verify(publicKey, sig);
			MyClass o = (MyClass) so.getObject();
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
	}

	public boolean verificarAssinatura(PublicKey key, byte[] buffer,
			byte[] signature) throws AssinaturaDigitalException {
		try {
			Signature sig = Signature.getInstance(key.getAlgorithm());
			sig.initVerify(key);
			sig.update(buffer, 0, buffer.length);
			return sig.verify(signature);
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
	}
*/
	/**
	 * Realiza um digest em um array de bytes através do algoritmo especificado
	 * 
	 * @param input
	 *            - O array de bytes a ser criptografado
	 * @param algoritmo
	 *            - O algoritmo a ser utilizado
	 * @return byte[] - O resultado da criptografia
	 * @throws NoSuchAlgorithmException
	 *             - Caso o algoritmo fornecido não seja válido
	 */
/*	
	public byte[] digest(byte[] input, String algoritmo)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algoritmo);
		md.reset();
		return md.digest(input);
	}
*/

	/**
	 * Responsável por remeter dados assinados digitalmente a um destinatário.
	 * Juntamente com a assinatura digital é fornecida uma chave pública.
	 *
	 */
/*	
	public AssinaturaDigitalDados remeterDadosAssinados(String mensagem,
			String arquivoProperties, String arquivoChavePublica)
			throws AssinaturaDigitalException {
		AssinaturaDigitalDados da = new AssinaturaDigitalDados();
		try {
			// Gera chaves pública e privada
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			SecureRandom secRan = new SecureRandom();
			keyGen.initialize(512, secRan);
			KeyPair keyP = keyGen.generateKeyPair();
			PublicKey pubKey = keyP.getPublic();
			PrivateKey priKey = keyP.getPrivate();

			// System.out.println("Pubkey: " + pubKey);
			// System.out.println("Serial:" + pubKey.serialVersionUID);

			// Obtem algoritmo para geração da assinatura
			Signature geradorAss = Signature.getInstance("DSA");

			// Inicializar geração
			geradorAss.initSign(priKey);

			// Gerar assinatura
			geradorAss.update(mensagem.getBytes());
			byte[] assinatura = geradorAss.sign();
			String sAssinatura = Hex.encodeHexString(assinatura);

			// Grava a mensagem num arquivo properties
			Properties p = new Properties();
			p.put("mensagem", mensagem);
			p.put("assinatura", sAssinatura);
			p.store(new FileOutputStream(arquivoProperties), null);

			// Serializa a chave pública
			ObjectOutputStream oout = new ObjectOutputStream(
					new FileOutputStream(arquivoChavePublica));
			oout.writeObject(pubKey);
			oout.close();

			da.setMensagem(mensagem);
			da.setArquivoChavePublica(arquivoChavePublica);
			da.setArquivoProperties(arquivoProperties);
			da.setAssinatura(sAssinatura);
			da.setAssinaturaEmByte(assinatura);

		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, "Erro chave inválida, "
					+ e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log,
					"Erro sem algoritmo de criptografia, " + e.getMessage());
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, "Erro de assinatura, "
					+ e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log,
					"Erro de entrada/saída ao remeter dados assinados, "
							+ e.getMessage());
		}
		return da;
	}
*/

	/**
	 * Responsável por receber dados assinados digitalmente por um remetente.
	 * Juntamente com a assinatura digital é fornecida uma chave pública.
	 */
/*	
	public AssinaturaDigitalDados receberDadosAssinados(
			String arquivoChavePublica, String arquivoProperties)
			throws AssinaturaDigitalException {
		AssinaturaDigitalDados da = new AssinaturaDigitalDados();
		try {
			// Obtêm chave pública
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(
					arquivoChavePublica));
			PublicKey pubKey = (PublicKey) oin.readObject();
			oin.close();

			// Cria objeto signature
			Signature sig = Signature.getInstance("DSA");

			// Inicializa Signature com a chave pública
			sig.initVerify(pubKey);

			// Lê arquivo de properties com mensagem e assinatura
			Properties p = new Properties();
			p.load(new FileInputStream(arquivoProperties));

			// Captura mensagem
			String mensagem = (String) p.get("mensagem");
			String sAssinatura = (String) p.get("assinatura");
			// System.out.println("Mensagem: " + mensagem);
			// System.out.println("Assinatura: " + sAssinatura);

			// Captura assinatura
			byte[] assinatura = Hex.decodeHex(((String) p.get("assinatura")).toCharArray());

			// valida o dado
			sig.update(mensagem.getBytes());

			boolean bValida = sig.verify(assinatura);

			da.setMensagem(mensagem);
			da.setArquivoChavePublica(arquivoChavePublica);
			da.setArquivoProperties(arquivoProperties);
			da.setAssinatura(sAssinatura);
			da.setAssinaturaEmByte(assinatura);
			da.setAssinaturaValida(bValida);

		} catch (InvalidKeyException e) {
			throw new AssinaturaDigitalException(log, "Erro chave inválida, "
					+ e.getMessage());
		} catch (FileNotFoundException e) {
			throw new AssinaturaDigitalException(log,
					"Erro arquivo não encontrado, " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new AssinaturaDigitalException(log,
					"Erro sem algoritmo de criptografia, " + e.getMessage());
		} catch (SignatureException e) {
			throw new AssinaturaDigitalException(log, "Erro de assinatura, "
					+ e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log,
					"Erro de entrada/saída ao receber dados assinados, "
							+ e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new AssinaturaDigitalException(log,
					"Erro classe não encontrada, " + e.getMessage());
		} catch (DecoderException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}

		return da;
	}
*/
}
/*
class MyClass implements Serializable {
	private static final long serialVersionUID = 1L;

	String s = "my string";
	int i = 123;
}

class Nova implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (Callback callback : callbacks) {
			System.out.println(callback.toString());	
		}
		
	}
	
}
*/
