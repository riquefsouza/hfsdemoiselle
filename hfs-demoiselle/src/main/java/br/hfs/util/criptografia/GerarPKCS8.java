package br.hfs.util.criptografia;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Hex;

public class GerarPKCS8 {

	public static void main(String[] args) {

		try {
			// generate key pair
			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance("RSA");
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.genKeyPair();

			// extract the encoded private key, this is an unencrypted PKCS#8
			// private key
			byte[] encodedprivkey = keyPair.getPrivate().getEncoded();

			// We must use a PasswordBasedEncryption algorithm in order to
			// encrypt
			// the private key, you may use any common algorithm supported by
			// openssl, you can check them in the openssl documentation
			// http://www.openssl.org/docs/apps/pkcs8.html
			String MYPBEALG = "PBEWithSHA1AndDESede";
			String password = "pleaseChangeit!";

			int count = 20;// hash iteration count
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[8];
			random.nextBytes(salt);

			// Create PBE parameter set
			PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory keyFac = SecretKeyFactory.getInstance(MYPBEALG);
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

			Cipher pbeCipher = Cipher.getInstance(MYPBEALG);

			// Initialize PBE Cipher with key and parameters
			pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

			// Encrypt the encoded Private Key with the PBE key
			byte[] ciphertext = pbeCipher.doFinal(encodedprivkey);

			// Now construct PKCS #8 EncryptedPrivateKeyInfo object
			AlgorithmParameters algparms = AlgorithmParameters
					.getInstance(MYPBEALG);
			algparms.init(pbeParamSpec);
			EncryptedPrivateKeyInfo encinfo = new EncryptedPrivateKeyInfo(
					algparms, ciphertext);

			// and here we have it! a DER encoded PKCS#8 encrypted key!
			byte[] encryptedPkcs8 = encinfo.getEncoded();
			
			System.out.println(Hex.encodeHexString(encryptedPkcs8));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
