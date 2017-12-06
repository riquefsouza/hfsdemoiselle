package br.hfs.util.criptografia;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.DHParameterSpec;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import br.hfs.util.criptografia.CriptografiaUtil.GeradorParChaves;

public final class CriptografiaBCUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("CriptografiaBCUtil");
	 
	public List<ProvedorSeguranca> listarProvedores(boolean bBouncyCastle) {
		List<ProvedorSeguranca> lista = new ArrayList<ProvedorSeguranca>();
		ProvedorSeguranca ps;

		if (bBouncyCastle) {			
			if (!Arrays.toString(Security.getProviders()).contains("BC")) {
				Security.addProvider(new BouncyCastleProvider());
			}
		}		

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
	
	public Criptografia criptoAssimetrico(Criptografia params, GeradorParChaves algoritmo) throws CriptografiaException {
		boolean bBouncyCastle = false;
		
		try {

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
			
			KeyPairGenerator keyGen;
			
			if (params.getProvedor().isEmpty())
				keyGen = KeyPairGenerator.getInstance(algoritmo.name());
			else 
				keyGen = KeyPairGenerator.getInstance(algoritmo.name(), params.getProvedor());
			
			SecureRandom random = SecureRandom
					.getInstance("SHA1PRNG", "SUN");
			
			if (params.getTamanhoChave()!=0){
				keyGen.initialize(params.getTamanhoChave(), random);
			} else {
				if (algoritmo.equals(GeradorParChaves.ECDSA)){
					ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("prime192v1");
					keyGen.initialize(ecSpec, random);
				} else if (algoritmo.equals(GeradorParChaves.DH)){
					// The base used with the SKIP 1024 bit modulus
					BigInteger g512 = BigInteger.valueOf(2);

					// The 1024 bit Diffie-Hellman modulus values used by SKIP
					byte skip1024ModulusBytes[] = { (byte) 0xF4,
					    (byte) 0x88, (byte) 0xFD, (byte) 0x58, (byte) 0x4E, (byte) 0x49,
					    (byte) 0xDB, (byte) 0xCD, (byte) 0x20, (byte) 0xB4, (byte) 0x9D,
					    (byte) 0xE4, (byte) 0x91, (byte) 0x07, (byte) 0x36, (byte) 0x6B,
					    (byte) 0x33, (byte) 0x6C, (byte) 0x38, (byte) 0x0D, (byte) 0x45,
					    (byte) 0x1D, (byte) 0x0F, (byte) 0x7C, (byte) 0x88, (byte) 0xB3,
					    (byte) 0x1C, (byte) 0x7C, (byte) 0x5B, (byte) 0x2D, (byte) 0x8E,
					    (byte) 0xF6, (byte) 0xF3, (byte) 0xC9, (byte) 0x23, (byte) 0xC0,
					    (byte) 0x43, (byte) 0xF0, (byte) 0xA5, (byte) 0x5B, (byte) 0x18,
					    (byte) 0x8D, (byte) 0x8E, (byte) 0xBB, (byte) 0x55, (byte) 0x8C,
					    (byte) 0xB8, (byte) 0x5D, (byte) 0x38, (byte) 0xD3, (byte) 0x34,
					    (byte) 0xFD, (byte) 0x7C, (byte) 0x17, (byte) 0x57, (byte) 0x43,
					    (byte) 0xA3, (byte) 0x1D, (byte) 0x18, (byte) 0x6C, (byte) 0xDE,
					    (byte) 0x33, (byte) 0x21, (byte) 0x2C, (byte) 0xB5, (byte) 0x2A,
					    (byte) 0xFF, (byte) 0x3C, (byte) 0xE1, (byte) 0xB1, (byte) 0x29,
					    (byte) 0x40, (byte) 0x18, (byte) 0x11, (byte) 0x8D, (byte) 0x7C,
					    (byte) 0x84, (byte) 0xA7, (byte) 0x0A, (byte) 0x72, (byte) 0xD6,
					    (byte) 0x86, (byte) 0xC4, (byte) 0x03, (byte) 0x19, (byte) 0xC8,
					    (byte) 0x07, (byte) 0x29, (byte) 0x7A, (byte) 0xCA, (byte) 0x95,
					    (byte) 0x0C, (byte) 0xD9, (byte) 0x96, (byte) 0x9F, (byte) 0xAB,
					    (byte) 0xD0, (byte) 0x0A, (byte) 0x50, (byte) 0x9B, (byte) 0x02,
					    (byte) 0x46, (byte) 0xD3, (byte) 0x08, (byte) 0x3D, (byte) 0x66,
					    (byte) 0xA4, (byte) 0x5D, (byte) 0x41, (byte) 0x9F, (byte) 0x9C,
					    (byte) 0x7C, (byte) 0xBD, (byte) 0x89, (byte) 0x4B, (byte) 0x22,
					    (byte) 0x19, (byte) 0x26, (byte) 0xBA, (byte) 0xAB, (byte) 0xA2,
					    (byte) 0x5E, (byte) 0xC3, (byte) 0x55, (byte) 0xE9, (byte) 0x2F,
					    (byte) 0x78, (byte) 0xC7 };

					// The SKIP 1024 bit modulus
					BigInteger p512 = new BigInteger(1, skip1024ModulusBytes);					
					
					 DHParameterSpec dhParams = new DHParameterSpec(p512, g512);
					 keyGen.initialize(dhParams, random);
				}
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
		} catch (InvalidAlgorithmParameterException e) {
			throw new CriptografiaException(log, e.getMessage());
		}
	}

}
