package br.hfs.util.pdf.assinatura;

import java.io.Serializable;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PdfAssinatura implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Date data;
	private boolean validoPKCS7;
	private List<Certificate> certificados;	
	private List<X509Certificate> certsX509;

	public PdfAssinatura() {
		super();
		this.certificados = new ArrayList<Certificate>();
		this.certsX509 = new ArrayList<X509Certificate>();;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isValidoPKCS7() {
		return validoPKCS7;
	}

	public void setValidoPKCS7(boolean validoPKCS7) {
		this.validoPKCS7 = validoPKCS7;
	}

	public List<Certificate> getCertificados() {
		return certificados;
	}

	public void setCertificados(List<Certificate> certificados) {
		this.certificados = certificados;
	}

	public List<X509Certificate> getCertsX509() {
		return certsX509;
	}

	public void setCertsX509(List<X509Certificate> certsX509) {
		this.certsX509 = certsX509;
	}

	@Override
	public String toString() {
		return "PdfAssinatura [nome=" + nome + ", data=" + data
				+ ", validoPKCS7=" + validoPKCS7 + ", certificados="
				+ certificados + ", certsX509=" + certsX509 + "]";
	}

}
