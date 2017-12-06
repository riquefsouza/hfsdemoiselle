package br.hfs.util.email;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.mail.Mail;
import br.gov.frameworkdemoiselle.mail.MailException;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class EmailMB implements Serializable {
	private static final long serialVersionUID = 1L;

	private String emailPara;
	private String emailCc;
	private String emailAssunto;
	private String emailTexto;

	@Inject
	private Mail mail;

	@PostConstruct
	public void init() {
		emailPara = "henrique.souza@trt1.jus.br";
		emailAssunto = "TESTE DE ENVIO DE EMAIL!";
		emailTexto = "Meu Primeiro E-mail";
	}

	public String enviarEmail() {
		try {
			mail.to(emailPara).from("henrique.souza@trt1.jus.br");
			if (!emailCc.isEmpty()) {
				mail.cc(emailCc);
			}
			mail.body().text(emailTexto).subject(emailAssunto).importance()
					.high().send();
		} catch (MailException e) {
			System.out.println("EMAIL ERRO: " + e.getMessage());
		}
		return "";
	}

	public String getEmailPara() {
		return emailPara;
	}

	public void setEmailPara(String emailPara) {
		this.emailPara = emailPara;
	}

	public String getEmailCc() {
		return emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public String getEmailAssunto() {
		return emailAssunto;
	}

	public void setEmailAssunto(String emailAssunto) {
		this.emailAssunto = emailAssunto;
	}

	public String getEmailTexto() {
		return emailTexto;
	}

	public void setEmailTexto(String emailTexto) {
		this.emailTexto = emailTexto;
	}

}
