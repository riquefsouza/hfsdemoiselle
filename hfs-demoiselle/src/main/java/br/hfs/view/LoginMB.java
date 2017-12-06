package br.hfs.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.util.Beans;
import br.hfs.AplicacaoConfig;
import br.hfs.security.Credenciais;
import br.hfs.util.Captcha;
import br.hfs.util.UserSessionController;

@SessionScoped
@Named("loginMB")
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String captchInserido;
	private String captchGerado;

	@Inject
	private SecurityContext securityContext;

	@Inject
	private MessageContext messageContext;

	@Inject
	private AplicacaoConfig config;

	@Inject
	private Credenciais credentials;	
	
	public LoginMB() {
		super();
	}

	public String getCaptchGerado() {
		return this.captchGerado;
	}

	public String getCaptchInserido() {
		return this.captchInserido;
	}

	private String getCodigoRandomico() {
		Random r = new Random();
		String token = Long.toString(Math.abs(r.nextLong()), 36);
		String ch = token.substring(0, 6);
		return ch;
	}

	public StreamedContent getDynamicCaptcha() throws IOException,
			ServletException {
		this.limparCaptch();
		Captcha captcha = new Captcha(45, 150);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		this.setCaptchGerado(this.getCodigoRandomico());
		ImageIO.write(captcha.gerarImg(this.getCaptchGerado()), "png", os);
		return new DefaultStreamedContent(new ByteArrayInputStream(
				os.toByteArray()), Captcha.EXTENSAO_IMAGEM);
	}

	public void limparCaptch() {
		this.setCaptchInserido(null);
	}

	public void setCaptchGerado(String captchGerado) {
		this.captchGerado = captchGerado;
	}

	public void setCaptchInserido(String captchInserido) {
		this.captchInserido = captchInserido;
	}

	public boolean validarCaptcha() {
		if (!this.getCaptchGerado().equalsIgnoreCase(this.getCaptchInserido())) {
			this.limparCaptch();
			messageContext.add("Valor digitado não confere com a imagem.");
			return false;
		}
		this.limparCaptch();
		return true;
	}
	
	private UserSessionController getSessionController() {
		UserSessionController sessionController = Beans.getReference(UserSessionController.class);
		return sessionController;
	}
	
	public String doLogin() {
		if (this.validarCaptcha()) {
			FacesContext currentInstance = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) currentInstance
					.getExternalContext().getSession(false);

			session.setAttribute("login", credentials.getLogin());
			session.getAttribute("login");
			
			session.setMaxInactiveInterval(config.getTempoMaxInativoSessao() * 60);

			try {
				securityContext.login();
				
				UserSessionController sessionController = this.getSessionController();
				sessionController.checkRepeatedLogin(session);
				
			} catch (Exception e) {
				messageContext.add(e.getMessage());
			}
		}
		return "";
	}

}
