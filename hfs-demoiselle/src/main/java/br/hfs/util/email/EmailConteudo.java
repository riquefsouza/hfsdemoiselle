package br.hfs.util.email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmailConteudo implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean htmlEmail;
	private String nomeServidor;
	private String emailDestino;
	private String nomeDestino;
	private String emailCCDestino;
	private String nomeCCDestino;
	private String emailOrigem;
	private String nomeOrigem;
	private String assunto;
	private String mensagem;
	private List<EmailAnexo> anexos;

	public EmailConteudo() {
		super();
		anexos = new ArrayList<EmailAnexo>();
	}

	public EmailConteudo(boolean htmlEmail, String nomeServidor,
			String emailDestino, String nomeDestino, String emailCCDestino,
			String nomeCCDestino, String emailOrigem, String nomeOrigem,
			String assunto, String mensagem) {
		this.htmlEmail = htmlEmail;
		this.nomeServidor = nomeServidor;
		this.emailDestino = emailDestino;
		this.nomeDestino = nomeDestino;
		this.emailCCDestino = emailCCDestino;
		this.nomeCCDestino = nomeCCDestino;
		this.emailOrigem = emailOrigem;
		this.nomeOrigem = nomeOrigem;
		this.assunto = assunto;
		this.mensagem = mensagem;
	}

	public EmailConteudo(boolean htmlEmail, String nomeServidorEmail,
			String emailDestino, String nomeDestino, String emailCCDestino,
			String nomeCCDestino, String emailOrigem, String nomeOrigem,
			String assunto, String mensagem, List<EmailAnexo> anexos) {
		this(htmlEmail, nomeServidorEmail, emailDestino, nomeDestino,
				emailCCDestino, nomeCCDestino, emailOrigem, nomeOrigem,
				assunto, mensagem);
		this.anexos = anexos;
	}

	public boolean isHtmlEmail() {
		return htmlEmail;
	}

	public void setHtmlEmail(boolean htmlEmail) {
		this.htmlEmail = htmlEmail;
	}

	public String getNomeServidor() {
		return nomeServidor;
	}

	public void setNomeServidor(String nomeServidor) {
		this.nomeServidor = nomeServidor;
	}

	public String getEmailDestino() {
		return emailDestino;
	}

	public void setEmailDestino(String emailDestino) {
		this.emailDestino = emailDestino;
	}

	public String getNomeDestino() {
		return nomeDestino;
	}

	public void setNomeDestino(String nomeDestino) {
		this.nomeDestino = nomeDestino;
	}

	public String getEmailOrigem() {
		return emailOrigem;
	}

	public void setEmailOrigem(String emailOrigem) {
		this.emailOrigem = emailOrigem;
	}

	public String getNomeOrigem() {
		return nomeOrigem;
	}

	public void setNomeOrigem(String nomeOrigem) {
		this.nomeOrigem = nomeOrigem;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<EmailAnexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<EmailAnexo> anexos) {
		this.anexos = anexos;
	}

	public String getEmailCCDestino() {
		return emailCCDestino;
	}

	public void setEmailCCDestino(String emailCCDestino) {
		this.emailCCDestino = emailCCDestino;
	}

	public String getNomeCCDestino() {
		return nomeCCDestino;
	}

	public void setNomeCCDestino(String nomeCCDestino) {
		this.nomeCCDestino = nomeCCDestino;
	}

	public String toString() {
		return "htmlEmail: " + htmlEmail + ", nomeServidor: " + nomeServidor
				+ ", emailDestino: " + emailDestino + ", nomeDestino: "
				+ nomeDestino + ", emailCCDestino: " + emailCCDestino
				+ ", nomeCCDestino: " + nomeCCDestino + ", emailOrigem: "
				+ emailOrigem + ", nomeOrigem: " + nomeOrigem + ", assunto: "
				+ assunto;
	}

}
