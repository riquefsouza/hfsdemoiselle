package br.hfs.view.apiexemplos;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.captcha.CaptchaException;
import br.hfs.util.captcha.CaptchaUtil;

@ManagedBean
public class JCaptchaView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String texto;

	private String caminho;

	@Inject
	private CaptchaUtil captchaUtil;
	
	@Inject
	private MessageContext messageContext;

	public void validar() {
		if (!texto.isEmpty()) {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
			String captchaId = request.getSession().getId();
			/*
			caminho = ec.getRequestScheme() + "://" + ec.getRequestServerName()
					+ ":" + ec.getRequestServerPort()
					+ ec.getRequestContextPath() + "captcha?texto=" + texto;
			*/
			try {
				if (captchaUtil.validar(captchaId, texto)){
					messageContext.add("A resposta ["+texto+"] está correta!");
				} else { 
					messageContext.add("A resposta ["+texto+"] NÃO está correta!");
				}
			} catch (CaptchaException e) {
				messageContext.add(e.getMessage());
			}
		} else {
			messageContext.add("Texto não pode ser vazio!");
		}

	}

	/*
	 * public void gerar() { FacesContext context =
	 * FacesContext.getCurrentInstance(); ExternalContext ec =
	 * context.getExternalContext(); try { //
	 * context.getExternalContext().dispatch(caminho);
	 * 
	 * String encodeURL = ec.encodeResourceURL(caminho);
	 * context.getExternalContext().redirect(encodeURL); } catch (IOException e)
	 * { messageContext.add("ERRO: " + e.getMessage()); } finally {
	 * context.responseComplete(); }
	 * 
	 * }
	 */
	public String getCaminho() {
		return caminho;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
