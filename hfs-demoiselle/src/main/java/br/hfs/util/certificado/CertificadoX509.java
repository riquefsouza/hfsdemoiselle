package br.hfs.util.certificado;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.hfs.util.security.CertificadoDigitalVO;

public class CertificadoX509 implements Serializable {
	private static final long serialVersionUID = 1L;

	private int version;
	private String subjectDN;
	private String signatureAlgorithm;
	private String publicKeyAlgorithm;
	private String publicKeyFormato;
	private String publicKey;
	private Date validityNotBefore;
	private Date validityNotAfter;
	private String serialNumber;
	private String IssuerDN;
	private String email;
	private String type;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getSubjectDN() {
		return subjectDN;
	}

	public void setSubjectDN(String subjectDN) {
		this.subjectDN = subjectDN;
	}

	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}

	public String getPublicKeyAlgorithm() {
		return publicKeyAlgorithm;
	}

	public void setPublicKeyAlgorithm(String publicKeyAlgorithm) {
		this.publicKeyAlgorithm = publicKeyAlgorithm;
	}

	public String getPublicKeyFormato() {
		return publicKeyFormato;
	}

	public void setPublicKeyFormato(String publicKeyFormato) {
		this.publicKeyFormato = publicKeyFormato;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public Date getValidityNotBefore() {
		return validityNotBefore;
	}

	public void setValidityNotBefore(Date validityNotBefore) {
		this.validityNotBefore = validityNotBefore;
	}

	public Date getValidityNotAfter() {
		return validityNotAfter;
	}

	public void setValidityNotAfter(Date validityNotAfter) {
		this.validityNotAfter = validityNotAfter;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIssuerDN() {
		return IssuerDN;
	}

	public void setIssuerDN(String issuerDN) {
		IssuerDN = issuerDN;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<CertificadoDigitalVO> toLista(){
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		List<CertificadoDigitalVO> lista = new ArrayList<CertificadoDigitalVO>();
		lista.add(new CertificadoDigitalVO("Versão", Integer.toString(version)));
		lista.add(new CertificadoDigitalVO("Nome Distinto do Sujeito", subjectDN));
		lista.add(new CertificadoDigitalVO("Algoritmo da Assinatura", signatureAlgorithm));
		lista.add(new CertificadoDigitalVO("Algoritmo da Chave Pública", publicKeyAlgorithm));
		lista.add(new CertificadoDigitalVO("Formato da Chave Pública", publicKeyFormato));
		lista.add(new CertificadoDigitalVO("Chave Pública", publicKey));
		lista.add(new CertificadoDigitalVO("Válido a partir da Data", sm.format(validityNotBefore)));
		lista.add(new CertificadoDigitalVO("Válido até a Data", sm.format(validityNotAfter)));		
		lista.add(new CertificadoDigitalVO("Nome Distinto do Emitente", IssuerDN));
		lista.add(new CertificadoDigitalVO("Número Serial", serialNumber));
		lista.add(new CertificadoDigitalVO("E-mail do sujeito", email));
		lista.add(new CertificadoDigitalVO("Tipo do Certificado", type));
		return lista;
	}
	
}
