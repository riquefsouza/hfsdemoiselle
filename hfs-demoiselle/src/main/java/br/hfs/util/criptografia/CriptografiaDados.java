package br.hfs.util.criptografia;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CriptografiaDados {

	private BigInteger primoDSA;
	private BigInteger subPrimoDSA;
	private BigInteger baseDSA;
	private BigInteger xChavePrivadaDSA;
	private BigInteger yChavePublicaDSA;

	private PrivateKey chavePrivada;
	private PublicKey chavePublica;
	private String formatoChavePrivada;
	private String formatoChavePublica;
	private byte[] bytesChavePrivada;
	private byte[] bytesChavePublica;
	private boolean chavesPrivadasIguais;
	private boolean chavesPublicasIguais;

	public CriptografiaDados() {
		super();
	}

	public BigInteger getPrimoDSA() {
		return primoDSA;
	}

	public void setPrimoDSA(BigInteger primoDSA) {
		this.primoDSA = primoDSA;
	}

	public BigInteger getSubPrimoDSA() {
		return subPrimoDSA;
	}

	public void setSubPrimoDSA(BigInteger subPrimoDSA) {
		this.subPrimoDSA = subPrimoDSA;
	}

	public BigInteger getBaseDSA() {
		return baseDSA;
	}

	public void setBaseDSA(BigInteger baseDSA) {
		this.baseDSA = baseDSA;
	}

	public BigInteger getXChavePrivadaDSA() {
		return xChavePrivadaDSA;
	}

	public void setXChavePrivadaDSA(BigInteger xChavePrivadaDSA) {
		this.xChavePrivadaDSA = xChavePrivadaDSA;
	}

	public BigInteger getYChavePublicaDSA() {
		return yChavePublicaDSA;
	}

	public void setYChavePublicaDSA(BigInteger yChavePublicaDSA) {
		this.yChavePublicaDSA = yChavePublicaDSA;
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

	public String getFormatoChavePrivada() {
		return formatoChavePrivada;
	}

	public void setFormatoChavePrivada(String formatoChavePrivada) {
		this.formatoChavePrivada = formatoChavePrivada;
	}

	public String getFormatoChavePublica() {
		return formatoChavePublica;
	}

	public void setFormatoChavePublica(String formatoChavePublica) {
		this.formatoChavePublica = formatoChavePublica;
	}

	public byte[] getBytesChavePrivada() {
		return bytesChavePrivada;
	}

	public void setBytesChavePrivada(byte[] bytesChavePrivada) {
		this.bytesChavePrivada = bytesChavePrivada;
	}

	public byte[] getBytesChavePublica() {
		return bytesChavePublica;
	}

	public void setBytesChavePublica(byte[] bytesChavePublica) {
		this.bytesChavePublica = bytesChavePublica;
	}

	public boolean isChavesPrivadasIguais() {
		return chavesPrivadasIguais;
	}

	public void setChavesPrivadasIguais(boolean chavesPrivadasIguais) {
		this.chavesPrivadasIguais = chavesPrivadasIguais;
	}

	public boolean isChavesPublicasIguais() {
		return chavesPublicasIguais;
	}

	public void setChavesPublicasIguais(boolean chavesPublicasIguais) {
		this.chavesPublicasIguais = chavesPublicasIguais;
	}

}
