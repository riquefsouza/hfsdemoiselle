package br.hfs.util.security;

import java.io.Serializable;
import java.security.Key;

import org.apache.commons.codec.binary.Hex;

import br.gov.frameworkdemoiselle.certificate.criptography.AsymmetricAlgorithmEnum;
import br.gov.frameworkdemoiselle.certificate.criptography.SymmetricAlgorithmEnum;

public class CriptografiaParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private byte[] frase;
	private Key chaveUnica;
	private Key chavePrivada;
	private Key chavePublica;
	private SymmetricAlgorithmEnum algoritmoSimetrico;
	private AsymmetricAlgorithmEnum algoritmoAssimetrico;
	private byte[] criptografado;
	private byte[] descriptografado;

	public byte[] getFrase() {
		return frase;
	}

	public void setFrase(byte[] frase) {
		this.frase = frase;
	}

	public Key getChaveUnica() {
		return chaveUnica;
	}

	public void setChaveUnica(Key chaveUnica) {
		this.chaveUnica = chaveUnica;
	}

	public Key getChavePrivada() {
		return chavePrivada;
	}

	public void setChavePrivada(Key chavePrivada) {
		this.chavePrivada = chavePrivada;
	}

	public Key getChavePublica() {
		return chavePublica;
	}

	public void setChavePublica(Key chavePublica) {
		this.chavePublica = chavePublica;
	}

	public SymmetricAlgorithmEnum getAlgoritmoSimetrico() {
		return algoritmoSimetrico;
	}

	public void setAlgoritmoSimetrico(SymmetricAlgorithmEnum algoritmoSimetrico) {
		this.algoritmoSimetrico = algoritmoSimetrico;
	}

	public AsymmetricAlgorithmEnum getAlgoritmoAssimetrico() {
		return algoritmoAssimetrico;
	}

	public void setAlgoritmoAssimetrico(
			AsymmetricAlgorithmEnum algoritmoAssimetrico) {
		this.algoritmoAssimetrico = algoritmoAssimetrico;
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
	
}
