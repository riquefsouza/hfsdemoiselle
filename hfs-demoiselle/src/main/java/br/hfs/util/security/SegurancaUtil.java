package br.hfs.util.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.SecretKey;

import org.apache.commons.io.FileUtils;

import junit.framework.Assert;
import br.gov.frameworkdemoiselle.certificate.CertificateLoader;
import br.gov.frameworkdemoiselle.certificate.CertificateLoaderImpl;
import br.gov.frameworkdemoiselle.certificate.CertificateManager;
import br.gov.frameworkdemoiselle.certificate.criptography.AsymmetricAlgorithmEnum;
import br.gov.frameworkdemoiselle.certificate.criptography.Criptography;
import br.gov.frameworkdemoiselle.certificate.criptography.Digest;
import br.gov.frameworkdemoiselle.certificate.criptography.DigestAlgorithmEnum;
import br.gov.frameworkdemoiselle.certificate.criptography.factory.CriptographyFactory;
import br.gov.frameworkdemoiselle.certificate.criptography.factory.DigestFactory;
import br.gov.frameworkdemoiselle.certificate.keystore.loader.KeyStoreLoader;
import br.gov.frameworkdemoiselle.certificate.keystore.loader.factory.KeyStoreLoaderFactory;
import br.gov.frameworkdemoiselle.certificate.signer.SignerAlgorithmEnum;
import br.gov.frameworkdemoiselle.certificate.signer.factory.PKCS1Factory;
import br.gov.frameworkdemoiselle.certificate.signer.factory.PKCS7Factory;
import br.gov.frameworkdemoiselle.certificate.signer.pkcs1.PKCS1Signer;
import br.gov.frameworkdemoiselle.certificate.signer.pkcs7.PKCS7Signer;

public final class SegurancaUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public byte[] HashSimples(byte[] frase, DigestAlgorithmEnum algoritmo) {
		Digest digest = DigestFactory.getInstance().factoryDefault();
		digest.setAlgorithm(algoritmo);
		byte[] resumo = digest.digest(frase);
		return resumo;
	}

	public String HashSimplesHex(byte[] frase,
			DigestAlgorithmEnum algoritmo) {
		Digest digest = DigestFactory.getInstance().factoryDefault();
		digest.setAlgorithm(algoritmo);
		String resumo = digest.digestHex(frase);
		return resumo;
	}

	public byte[] HashArquivo(File arquivo, DigestAlgorithmEnum algoritmo) {
		Digest digest = DigestFactory.getInstance().factoryDefault();
		digest.setAlgorithm(algoritmo);
		byte[] resumo = digest.digestFile(arquivo);
		return resumo;
	}

	public String HashArquivoHex(File arquivo,
			DigestAlgorithmEnum algoritmo) {
		Digest digest = DigestFactory.getInstance().factoryDefault();
		digest.setAlgorithm(algoritmo);
		String resumo = digest.digestFileHex(arquivo);
		return resumo;
	}

	public CriptografiaParams criptografiaSimetrica(
			CriptografiaParams params, boolean cifrar) {
		Criptography criptography = CriptographyFactory.getInstance()
				.factoryDefault();

		/* Alterando algoritmo */
		criptography.setAlgorithm(params.getAlgoritmoSimetrico());
		criptography.setKeyAlgorithm(params.getAlgoritmoSimetrico().toString()); 
		criptography.setSize(128);

		/* Geracao da chave unica */
		if (cifrar) {
			SecretKey chave = (SecretKey) criptography.generateKey();
			params.setChaveUnica(chave);
		}
		criptography.setKey(params.getChaveUnica());

		if (cifrar) {
			byte[] conteudo_criptografado = criptography.cipher(params
					.getFrase());
			params.setCriptografado(conteudo_criptografado);
		} else {
			byte[] conteudo_descriptografado = criptography.decipher(params
					.getCriptografado());
			params.setDescriptografado(conteudo_descriptografado);
		}
		return params;
	}

	public CriptografiaParams criptografiaAssimetrica(
			CriptografiaParams params, boolean cifrar) {
		Criptography criptography = CriptographyFactory.getInstance()
				.factoryDefault();

		/* Alterando algoritmo */
		criptography.setAlgorithm(params.getAlgoritmoAssimetrico());
		criptography.setKeyAlgorithm(params.getAlgoritmoAssimetrico().toString());		
		//criptography.setProvider(Security.getProvider("SUN"));
		//criptography.setProvider(null);
		criptography.setSize(1024);

		if (cifrar) {
			try {
				KeyPairGenerator keyGen = KeyPairGenerator.getInstance(params.getAlgoritmoAssimetrico().toString()); //, provedor);
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
				keyGen.initialize(1024, random);
				//keyGen.initialize(1024);
	
				KeyPair keypair = keyGen.generateKeyPair();
				PrivateKey chavePrivada = keypair.getPrivate();
				PublicKey chavePublica = keypair.getPublic();
				
				params.setChavePublica(chavePublica);
				params.setChavePrivada(chavePrivada);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
			}
		}
		
		/* Cifragem */
		if (cifrar) {
			criptography.setKey(params.getChavePublica());

			byte[] conteudoCriptografado = criptography.cipher(params.getFrase());
			params.setCriptografado(conteudoCriptografado);
		} else {
			/* Decifragem */
			criptography.setKey(params.getChavePrivada());

			byte[] conteudoDescriptografado = criptography.decipher(params
					.getCriptografado());
			params.setDescriptografado(conteudoDescriptografado);
		}
		return params;
	}

	public void CertificadoA1() {
		try {
			/* Obtendo a chave publica */
			File file = new File("/home/03397040477/public.der");
			byte[] encodedPublicKey = new byte[(int) file.length()];
			InputStream inputStreamPublicKey = new FileInputStream(file);
			inputStreamPublicKey.read(encodedPublicKey);
			inputStreamPublicKey.close();
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
					encodedPublicKey);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PublicKey publicKey = kf.generatePublic(publicKeySpec);

			/* Obtendo a chave privada */
			file = new File("/home/03397040477/private.pk8");
			byte[] encodedPrivateKey = new byte[(int) file.length()];
			InputStream inputStreamPrivateKey = new FileInputStream(file);
			inputStreamPrivateKey.read(encodedPrivateKey);
			inputStreamPrivateKey.close();
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
					encodedPrivateKey);
			kf = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = kf.generatePrivate(privateKeySpec);

			/* Cifragem */
			Criptography cripto = CriptographyFactory.getInstance()
					.factoryDefault();
			cripto.setAlgorithm(AsymmetricAlgorithmEnum.RSA);
			cripto.setKey(privateKey);
			byte[] conteudoCriptografado = cripto.cipher("SERPRO".getBytes());
			System.out.println(conteudoCriptografado);

			/* Decifragem */
			Criptography cripto2 = CriptographyFactory.getInstance()
					.factoryDefault();
			cripto2.setAlgorithm(AsymmetricAlgorithmEnum.RSA);
			cripto2.setKey(publicKey);

			byte[] conteudoDescriptografado = cripto2
					.decipher(conteudoCriptografado);
			System.out.println(new String(conteudoDescriptografado));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue("Configuracao nao carregada: " + e.getMessage(),
					false);
		}

	}

	public void CertificadoA3() {
		/* Senha do dispositivo */
		String PIN = "senha_do_token";
		try {
			/* Obtendo a chave privada */
			KeyStore keyStore = KeyStoreLoaderFactory.factoryKeyStoreLoader()
					.getKeyStore();
			String alias = (String) keyStore.aliases().nextElement();
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias,
					PIN.toCharArray());

			/* Obtendo a chave publica */
			CertificateLoader cl = new CertificateLoaderImpl();
			X509Certificate x509 = cl.loadFromToken(PIN);
			PublicKey publicKey = x509.getPublicKey();

			/* Configurando o Criptography */
			Criptography cripto = CriptographyFactory.getInstance()
					.factoryDefault();
			cripto.setAlgorithm(AsymmetricAlgorithmEnum.RSA);
			cripto.setProvider(keyStore.getProvider());

			/* criptografando com a chave privada */
			cripto.setKey(privateKey);
			byte[] conteudoCriptografado = cripto.cipher("SERPRO".getBytes());
			System.out.println(conteudoCriptografado);

			/* descriptografando com a chave publica */
			cripto.setKey(publicKey);
			byte[] conteudoAberto = cripto.decipher(conteudoCriptografado);
			System.out.println(new String(conteudoAberto));
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}
	
	public void assinaturaDigitalFormatoPKCS1(File arqKeyStore, String senha, String apelidoKeyStore, File arquivo, File arqAssinado) 
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException {
		/* conteudo a ser assinado */
		byte[] content = FileUtils.readFileToByteArray(arquivo);

		/* construindo um objeto PKCS1Signer atraves da fabrica */
		PKCS1Signer signer = PKCS1Factory.getInstance().factory();

		KeyStoreLoader keyStoreLoader = KeyStoreLoaderFactory
				.factoryKeyStoreLoader();
		KeyStore keystore = keyStoreLoader.getKeyStore();
		FileInputStream is = new FileInputStream(arqKeyStore);
		keystore.load(is, senha.toCharArray());
				
		signer.setAlgorithm(SignerAlgorithmEnum.SHA1withRSA);		
		signer.setPrivateKey((PrivateKey) keystore.getKey(apelidoKeyStore, senha.toCharArray()));
		//signer.setSignaturePolicy(PolicyFactory.Policies.AD_RB_CADES_2_1);

		/* Assinando um conjunto de bytes */
		byte[] sign = signer.doSign(content);
		
		FileUtils.writeByteArrayToFile(arqAssinado, sign);
	}

	public boolean validarAssinaturaFormatoPKCS1(File arquivo, File arqAssinado) 
			throws IOException{
		byte[] content = FileUtils.readFileToByteArray(arquivo);
		byte[] signed = FileUtils.readFileToByteArray(arqAssinado);

		PKCS1Signer signer = PKCS1Factory.getInstance().factoryDefault();

		return signer.check(content, signed);
	}

	public void assinaturaDigitalFormatoPKCS7(File arqKeyStore, String senha, String apelidoKeyStore, File arquivo, File arqAssinado) 
			throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException {
		byte[] content = FileUtils.readFileToByteArray(arquivo);

		PKCS7Signer signer = PKCS7Factory.getInstance().factoryDefault();

		KeyStoreLoader keyStoreLoader = KeyStoreLoaderFactory
				.factoryKeyStoreLoader();
		KeyStore keystore = keyStoreLoader.getKeyStore();
		FileInputStream is = new FileInputStream(arqKeyStore);
		keystore.load(is, senha.toCharArray());
		
		signer.setCertificates(keystore.getCertificateChain(apelidoKeyStore));
		signer.setPrivateKey((PrivateKey) keystore.getKey(apelidoKeyStore, senha.toCharArray()));
		//signer.setSignaturePolicy(PolicyFactory.Policies.AD_RB_CADES_2_1);
		
		byte[] sign = signer.doSign(content);		
		
		FileUtils.writeByteArrayToFile(arqAssinado, sign);
	}
	
	public boolean validarAssinaturaFormatoPKCS7(File arquivo, File arqAssinado) 
			throws IOException{
		byte[] content = FileUtils.readFileToByteArray(arquivo);
		byte[] signed = FileUtils.readFileToByteArray(arqAssinado);

		PKCS7Signer signer = PKCS7Factory.getInstance().factoryDefault();

		return signer.check(content, signed);
	}

	public PrivateKey carregarChavePrivadaDeArquivo(String arquivoPKCS8)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		Path path = Paths.get(arquivoPKCS8);
		byte[] conteudo = Files.readAllBytes(path);

		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(conteudo);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey chavePrivada = keyFactory.generatePrivate(privateKeySpec);
		return chavePrivada;
	}

	public PrivateKey carregarChavePrivadaDeToken(String senhaPIN)
			throws KeyStoreException, UnrecoverableKeyException,
			NoSuchAlgorithmException {
		KeyStoreLoader keyStoreLoader = KeyStoreLoaderFactory
				.factoryKeyStoreLoader();
		KeyStore keyStore = keyStoreLoader.getKeyStore();
		String certificateAlias = keyStore.aliases().nextElement();
		PrivateKey chavePrivada = (PrivateKey) keyStore.getKey(
				certificateAlias, senhaPIN.toCharArray());
		return chavePrivada;
	}

	public PublicKey carregarChavePublicaDeArquivo(String arquivoPKCS8)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		Path path = Paths.get(arquivoPKCS8);
		byte[] conteudo = Files.readAllBytes(path);

		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(conteudo);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey chavePublica = keyFactory.generatePublic(publicKeySpec);
		return chavePublica;
	}

	public PublicKey carregarChavePublicaDeToken()
			throws KeyStoreException, UnrecoverableKeyException,
			NoSuchAlgorithmException {
		KeyStoreLoader keyStoreLoader = KeyStoreLoaderFactory
				.factoryKeyStoreLoader();
		KeyStore keyStore = keyStoreLoader.getKeyStore();
		CertificateLoader certificateLoader = new CertificateLoaderImpl();
		certificateLoader.setKeyStore(keyStore);
		X509Certificate certificate = certificateLoader.loadFromToken();
		PublicKey chavePublica = certificate.getPublicKey();
		return chavePublica;
	}

	public CertificadoDigital carregarCertificadoDigitalDeArquivo(
			String arquivoCER, boolean validar) {
		File certFile = new File(arquivoCER);
		CertificateManager cm = new CertificateManager(certFile, validar);
		CertificadoDigital cert = cm.load(CertificadoDigital.class);
		return cert;
	}

	public X509Certificate carregarCertificadoDigitalDeArquivo(
			File arquivoCER) {
		CertificateLoader certificateLoader = new CertificateLoaderImpl();
		X509Certificate certificate = certificateLoader.load(
				arquivoCER);
		return certificate;
	}

	public X509Certificate carregarCertificadoDigitalDeToken() {
		KeyStoreLoader keyStoreLoader = KeyStoreLoaderFactory
				.factoryKeyStoreLoader();
		KeyStore keyStore = keyStoreLoader.getKeyStore();
		CertificateLoader certificateLoader = new CertificateLoaderImpl();
		certificateLoader.setKeyStore(keyStore);
		X509Certificate certificate = certificateLoader.loadFromToken();
		return certificate;
	}

	public CertificadoDigital carregarCertificadoDigitalDeToken(boolean validar) {
		X509Certificate xcert = this.carregarCertificadoDigitalDeToken();
		CertificateManager cm = new CertificateManager(xcert, validar);
		CertificadoDigital cert = cm.load(CertificadoDigital.class);
		return cert;
	}
	
}
