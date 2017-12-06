package br.hfs.util.criptografia;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public final class CriptografiaUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("CriptografiaUtil");
	 
	//DiffieHellman = DH
	//EllipticCurve = ECDSA
	public enum GeradorParChaves {
		DSA, RSA, DH, ECDSA
	}

	public enum GeradorChave {
		Blowfish, Rijndael, DESede, ARCFOUR, RC2, RC4, DES, AES, TripleDES
	}

	public List<ProvedorSeguranca> listarProvedores() {
		List<ProvedorSeguranca> lista = new ArrayList<ProvedorSeguranca>();
		ProvedorSeguranca ps;

		Provider p[] = Security.getProviders();
		for (int i = 0; i < p.length; i++) {
			ps = new ProvedorSeguranca();
			
			ps.setProvedor(p[i].toString());
			ps.setNome(p[i].getName());
			ps.setInfo(p[i].getInfo());
			ps.setVersao(p[i].getVersion());
			for (Enumeration<Object> e = p[i].keys(); e.hasMoreElements();){
				ps.getServicos().add(e.nextElement().toString());
			}
			lista.add(ps);
		}

		return lista;
	}
	
	public Criptografia criptografiaSimetrica(GeradorChave algoritmo,
			Criptografia params, boolean cifrar) throws CriptografiaException {
		try {
			
			if (cifrar) {
				if (algoritmo.equals(GeradorChave.DESede) || 
						algoritmo.equals(GeradorChave.TripleDES)){
					params.setTamanhoChave(168); // 112 ou 168
				} else if (algoritmo.equals(GeradorChave.DES)){
					params.setTamanhoChave(56);
				} else {
					params.setTamanhoChave(128);
				}			

				KeyGenerator keyGen = KeyGenerator.getInstance(algoritmo
						.name());

				SecureRandom random = SecureRandom.getInstance("SHA1PRNG",
						"SUN");
				keyGen.init(params.getTamanhoChave(), random);

				SecretKey chave = keyGen.generateKey();

				Cipher ecipher = Cipher.getInstance(algoritmo.name());
				ecipher.init(Cipher.ENCRYPT_MODE, chave);

				byte[] frase = params.getFrase();
				params.setCriptografado(ecipher.doFinal(frase));
				params.setChaveUnica(chave);
			} else {
				Cipher dcipher = Cipher.getInstance(algoritmo.name());
				dcipher.init(Cipher.DECRYPT_MODE, params.getChaveUnica());

				byte[] saida = dcipher.doFinal(params.getCriptografado());
				params.setDescriptografado(saida);
			}
		} catch (InvalidKeyException ex) {
			throw new CriptografiaException(log, ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			throw new CriptografiaException(log, ex.getMessage());
		} catch (NoSuchAlgorithmException ex) {
			throw new CriptografiaException(log, ex.getMessage());
		} catch (BadPaddingException ex) {
			throw new CriptografiaException(log, ex.getMessage());
		} catch (IllegalBlockSizeException ex) {
			throw new CriptografiaException(log, ex.getMessage());
		} catch (NoSuchProviderException ex) {
			throw new CriptografiaException(log, ex.getMessage());
		}
		return params;
	}

	public String[] listarGeradoresChave() {
		return getServicoCriptografia("KeyGenerator");
	}

	public String[] listarGeradoresParChaves() {
		return getServicoCriptografia("KeyPairGenerator");
	}

	public String[] getTodosServicosCriptografia() {
		Set<Object> result = new HashSet<Object>();
		// All all providers
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			// Get services provided by each provider
			Set<Object> keys = providers[i].keySet();
			for (Iterator<Object> it = keys.iterator(); it.hasNext();) {
				String key = (String) it.next();
				key = key.split(" ")[0];
				if (key.startsWith("Alg.Alias.")) {
					// Strip the alias
					key = key.substring(10);
				}
				int ix = key.indexOf('.');
				result.add(key.substring(0, ix));
			}
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	public String[] getServicoCriptografia(String serviceType) {
		Set<Object> result = new HashSet<Object>();
		// All all providers
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			// Get services provided by each provider
			Set<Object> keys = providers[i].keySet();
			for (Iterator<Object> it = keys.iterator(); it.hasNext();) {
				String key = (String) it.next();
				key = key.split(" ")[0];
				if (key.startsWith(serviceType + ".")) {
					result.add(key.substring(serviceType.length() + 1));
				} else if (key.startsWith("Alg.Alias." + serviceType + ".")) {
					// This is an alias
					result.add(key.substring(serviceType.length() + 11));
				}
			}
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	public long gerarNumeroRandomSeguro(int tamanho)
			throws CriptografiaException {
		try {

			if (tamanho > Long.SIZE)
				throw new CriptografiaException(log,
						"Tamanho maior do que Long.SIZE");

			// Create a secure random number generator
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			// // Get 1024 random bits
			// byte[] bytes = new byte[1024 / 8];
			// sr.nextBytes(bytes);
			byte[] seed = sr.generateSeed(tamanho);
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(seed);
			return sr.nextLong();
		} catch (NoSuchAlgorithmException e) {
			throw new CriptografiaException(log, e.getMessage());
		}
	}

	/*
	 * The DSA requires three parameters to create a key pair – the prime (P),
	 * the subprime (Q), and the base (G). These three values are used to create
	 * a private key (called X) and a public key (called Y). This example
	 * creates a PrivateKey and PublicKey from a set of DSA parameters.
	 */
	public void criarChavesDSA(CriptografiaDados params)
			throws CriptografiaException {
		try {
			// Obtain the DSA parameters;
			// see Getting the Digital Signature Algorithm (DSA) Parameters of a
			// Key Pair

			// Create the DSA key factory
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			// Create the DSA private key
			KeySpec privateKeySpec = new DSAPrivateKeySpec(
					params.getXChavePrivadaDSA(), params.getPrimoDSA(),
					params.getSubPrimoDSA(), params.getBaseDSA());
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			params.setChavePrivada(privateKey);
			// Create the DSA public key
			KeySpec publicKeySpec = new DSAPublicKeySpec(
					params.getYChavePublicaDSA(), params.getPrimoDSA(),
					params.getSubPrimoDSA(), params.getBaseDSA());
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			params.setChavePublica(publicKey);
		} catch (InvalidKeySpecException e) {
			throw new CriptografiaException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CriptografiaException(log, e.getMessage());
		}
	}

	public CriptografiaDados getChavesDSA() throws CriptografiaException {
		CriptografiaDados params = new CriptografiaDados();
		try {
			// Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			keyGen.initialize(1024);
			KeyPair keypair = keyGen.genKeyPair();
			DSAPrivateKey privateKey = (DSAPrivateKey) keypair.getPrivate();
			DSAPublicKey publicKey = (DSAPublicKey) keypair.getPublic();

			// Get p, q, g; they are the same for both private and public keys
			DSAParams dsaParams = privateKey.getParams();
			BigInteger primo = dsaParams.getP();
			BigInteger subprimo = dsaParams.getQ();
			BigInteger base = dsaParams.getG();

			// Get the private key's X
			BigInteger chavePrivada = privateKey.getX();

			// Get the public key's Y
			BigInteger chavePublica = publicKey.getY();

			params.setPrimoDSA(primo);
			params.setSubPrimoDSA(subprimo);
			params.setBaseDSA(base);
			params.setXChavePrivadaDSA(chavePrivada);
			params.setYChavePublicaDSA(chavePublica);
			return params;
		} catch (NoSuchAlgorithmException e) {
			throw new CriptografiaException(log, e.getMessage());
		}
	}

	public CriptografiaDados geraParChaves(GeradorParChaves geradorParChaves,
			int tamanhoChave) throws CriptografiaException {
		CriptografiaDados params = new CriptografiaDados();
		try {
			// Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
			KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(geradorParChaves.name());
			keyGen.initialize(tamanhoChave);
			KeyPair keypair = keyGen.genKeyPair();
			PrivateKey privateKey = keypair.getPrivate();
			PublicKey publicKey = keypair.getPublic();

			// Generate a 576-bit DH key pair
			// keyGen = KeyPairGenerator.getInstance("DH");
			// keyGen.initialize(576);
			// keypair = keyGen.genKeyPair();
			// privateKey = keypair.getPrivate();
			// publicKey = keypair.getPublic();

			// Generate a 1024-bit RSA key pair
			// keyGen = KeyPairGenerator.getInstance("RSA");
			// keyGen.initialize(1024);
			// keypair = keyGen.genKeyPair();
			// privateKey = keypair.getPrivate();
			// publicKey = keypair.getPublic();
			params.setChavePrivada(privateKey);
			params.setChavePublica(publicKey);
			return params;
		} catch (NoSuchAlgorithmException e) {
			throw new CriptografiaException(log, e.getMessage());
		}
	}

	public CriptografiaDados getParChaves(GeradorParChaves geradorParChaves,
			int tamanhoChave) throws CriptografiaException {
		CriptografiaDados params = new CriptografiaDados();
		try {
			// Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
			KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(geradorParChaves.name());
			keyGen.initialize(tamanhoChave);
			KeyPair keypair = keyGen.genKeyPair();
			PrivateKey privateKey = keypair.getPrivate();
			PublicKey publicKey = keypair.getPublic();

			// Get the bytes of the public and private keys
			byte[] privateKeyBytes = privateKey.getEncoded();
			byte[] publicKeyBytes = publicKey.getEncoded();

			// Get the formats of the encoded bytes
			String formatPrivate = privateKey.getFormat(); // PKCS#8
			String formatPublic = publicKey.getFormat(); // X.509

			// The bytes can be converted back to public and private key objects
			KeyFactory keyFactory = KeyFactory.getInstance(geradorParChaves
					.name());
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
					privateKeyBytes);
			PrivateKey privateKey2 = keyFactory.generatePrivate(privateKeySpec);
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
					publicKeyBytes);
			PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);

			// The orginal and new keys are the same
			boolean samePrivate = privateKey.equals(privateKey2); // true
			boolean samePublic = publicKey.equals(publicKey2); // true

			params.setChavePrivada(privateKey);
			params.setChavePublica(publicKey);
			params.setBytesChavePrivada(privateKeyBytes);
			params.setBytesChavePublica(publicKeyBytes);
			params.setFormatoChavePrivada(formatPrivate);
			params.setFormatoChavePublica(formatPublic);
			params.setChavesPrivadasIguais(samePrivate);
			params.setChavesPublicasIguais(samePublic);
			return params;
		} catch (InvalidKeySpecException e) {
			throw new CriptografiaException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CriptografiaException(log, e.getMessage());
		}
	}
	
	public Criptografia criptoAssimetrico(Criptografia params) throws CriptografiaException {
		GeradorParChaves algoritmo = GeradorParChaves.RSA;
		
		//boolean bBouncyCastle = false;
		
		try {
			if (algoritmo.equals(GeradorParChaves.RSA)){
				params.setTamanhoChave(1024);
			}
			
			/*
			if (algoritmo.equals(GeradorParChaves.RSA)){
				params.setTamanhoChave(1024);
			} else if (algoritmo.equals(GeradorParChaves.ECDSA)){
				//params.setProvedor("SunEC");
				//params.setTamanhoChave(571);
				bBouncyCastle = true;
				params.setProvedor("BC");
				params.setTamanhoChave(0);
			} else if (algoritmo.equals(GeradorParChaves.DH)){
				//params.setProvedor("SunJCE");
				//params.setTamanhoChave(1024);
				bBouncyCastle = true;
				params.setProvedor("BC");				
				params.setTamanhoChave(0);
			} else if (algoritmo.equals(GeradorParChaves.DSA)){
				//params.setProvedor("SUN");
				bBouncyCastle = true;
				params.setProvedor("BC");
				params.setTamanhoChave(1024);
			}

			if (bBouncyCastle) {
				if (!Arrays.toString(Security.getProviders()).contains("BC")) {
					Security.addProvider(new BouncyCastleProvider());
				}
			}			
			*/
			
			KeyPairGenerator keyGen;
			
			if (params.getProvedor().isEmpty())
				keyGen = KeyPairGenerator.getInstance(algoritmo.name());
			else 
				keyGen = KeyPairGenerator.getInstance(algoritmo.name(), params.getProvedor());
			
			SecureRandom random = SecureRandom
					.getInstance("SHA1PRNG", "SUN");
			
			if (params.getTamanhoChave()!=0){
				keyGen.initialize(params.getTamanhoChave(), random);
			}

			KeyPair keypair = keyGen.generateKeyPair();
			PrivateKey privateKey = keypair.getPrivate();
			PublicKey publicKey = keypair.getPublic();

			Cipher ecipher, dcipher;
			
			if (params.getProvedor().isEmpty()) {
				ecipher = Cipher.getInstance(algoritmo.name());
				dcipher = Cipher.getInstance(algoritmo.name());
			} else {
				ecipher = Cipher.getInstance(algoritmo.name(), params.getProvedor());
				dcipher = Cipher.getInstance(algoritmo.name(), params.getProvedor());
			}
			
			ecipher.init(Cipher.ENCRYPT_MODE, publicKey);
			dcipher.init(Cipher.DECRYPT_MODE, privateKey);

			//byte[] utf8 = frase.getBytes("UTF-8");
			byte[] enc = ecipher.doFinal(params.getFrase());

			byte[] dutf8 = dcipher.doFinal(enc);
			//String saida = new String(dutf8, "UTF-8");
			String saida = new String(dutf8);

			//params.setFrase(frase.getBytes());
			params.setChavePrivada(privateKey);
			params.setChavePublica(publicKey);
			params.setCriptografado(enc);
			params.setDescriptografado(saida.getBytes());
			return params;

		} catch (InvalidKeyException ex) {
			throw new CriptografiaException(log, ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			throw new CriptografiaException(log, ex.getMessage());
		} catch (NoSuchAlgorithmException ex) {
			throw new CriptografiaException(log, ex.getMessage());
		} catch (IllegalBlockSizeException e) {
			throw new CriptografiaException(log, e.getMessage());
		} catch (BadPaddingException e) {
			throw new CriptografiaException(log, e.getMessage());
		} catch (NoSuchProviderException e) {
			throw new CriptografiaException(log, e.getMessage());
		}
	}

}
