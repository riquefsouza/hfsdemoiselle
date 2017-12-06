package br.hfs.util.criptografia;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Hex;

public class Criptografia implements Serializable {
	private static final long serialVersionUID = 1L;

	private byte[] frase;
	private SecretKey chaveUnica;
	private PrivateKey chavePrivada;
	private PublicKey chavePublica;
	private byte[] criptografado;
	private byte[] descriptografado;
	private int tamanhoChave;
	private String provedor;
	
	public Criptografia() {
		super();
		this.tamanhoChave = 0;
		this.provedor = "";
	}

	public byte[] getFrase() {
		return frase;
	}

	public void setFrase(byte[] frase) {
		this.frase = frase;
	}

	public SecretKey getChaveUnica() {
		return chaveUnica;
	}

	public void setChaveUnica(SecretKey chaveUnica) {
		this.chaveUnica = chaveUnica;
	}

	public PrivateKey getChavePrivada() {
		return chavePrivada;
	}

	public void setChavePrivada(PrivateKey chavePrivada) {
		this.chavePrivada = chavePrivada;
	}

	public PublicKey getChavePublica() {
		return chavePublica;
	}

	public void setChavePublica(PublicKey chavePublica) {
		this.chavePublica = chavePublica;
	}

	public byte[] getCriptografado() {
		return criptografado;
	}

	public void setCriptografado(byte[] criptografado) {
		this.criptografado = criptografado;
	}

	public byte[] getDescriptografado() {
		return descriptografado;
	}

	public void setDescriptografado(byte[] descriptografado) {
		this.descriptografado = descriptografado;
	}

	public String getCriptografadoToHexadecimal() {
		return Hex.encodeHexString(criptografado);
	}

	public String getDescriptografadoToString() {
		return new String(descriptografado);
	}

	public int getTamanhoChave() {
		return tamanhoChave;
	}

	public void setTamanhoChave(int tamanhoChave) {
		this.tamanhoChave = tamanhoChave;
	}

	public String getProvedor() {
		return provedor;
	}

	public void setProvedor(String provedor) {
		this.provedor = provedor;
	}

}
