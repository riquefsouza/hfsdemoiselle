package br.hfs.util.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EnviarEmailExemplo {

	public static void main(String[] args) {
		try {
			
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthentication("riquefsouza@gmail.com", "");
			email.setAuthenticator(new DefaultAuthenticator("riquefsouza@gmail.com", ""));
			email.setSSLOnConnect(true);
			email.setDebug(true);
			email.setStartTLSEnabled(true);
			email.setStartTLSRequired(true);
			email.setFrom("riquefsouza@gmail.com");
			email.setSubject("TestMail");
			email.setMsg("This is a test mail ... :-)");
			email.setSslSmtpPort("465");
			email.addTo("henrique.souza@trt1.jus.br");
			String envio = email.send();
			System.out.println(envio);
			
			/*
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("riquefsouza@gmail.com", ""));
			email.setSSLOnConnect(true);
			email.setFrom("riquefsouza@gmail.com");
			email.setSubject("TestMail");
			email.setMsg("This is a test mail ... :-)");
			email.addTo("riquefsouza@gmail.com");
			String envio = email.send();
			System.out.println(envio);
			*/
		} catch (EmailException e) {
			System.out.println("ERRO: " + e.getMessage());
		}
		

	}

}
