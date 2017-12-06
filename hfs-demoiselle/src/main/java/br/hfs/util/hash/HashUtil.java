package br.hfs.util.hash;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import br.hfs.util.criptografia.CriptografiaUtil;

public final class HashUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("HashUtil");

	private CriptografiaUtil criptografiaUtil;

	public HashUtil() {
		criptografiaUtil = new CriptografiaUtil(); 
	}
	
	public enum TipoDigest {
		MD2, MD5, SHA1, SHA256, SHA384, SHA512
	}
	
	private String getTipoDigest(TipoDigest tipo) {
		String retorno = "";
		switch (tipo) {
		case MD2:
			retorno = "MD2";
			break;
		case MD5:
			retorno = "MD5";
			break;
		case SHA1:
			retorno = "SHA-1";
			break;
		case SHA256:
			retorno = "SHA-256";
			break;
		case SHA384:
			retorno = "SHA-384";
			break;
		case SHA512:
			retorno = "SHA-512";
			break;
		}
		return retorno;
	}

	// MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
	public byte[] getHash(String tipo, byte[] frase) throws HashException {
		try {
			MessageDigest md = null;
			md = MessageDigest.getInstance(tipo);
			return md.digest(frase);
		} catch (NoSuchAlgorithmException e) {
			throw new HashException(log, e.getMessage());
		}
	}

	public String[] listarAlgoritmosHash() {
		return criptografiaUtil.getServicoCriptografia("MessageDigest");
	}

	public byte[] getHash(TipoDigest tipo, byte[] frase) throws HashException {
		return getHash(getTipoDigest(tipo), frase);
	}

}
