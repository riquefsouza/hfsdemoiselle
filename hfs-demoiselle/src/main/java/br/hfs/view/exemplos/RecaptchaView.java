package br.hfs.view.exemplos;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class RecaptchaView implements Serializable {
	private static final long serialVersionUID = 1L;

	public void submit() {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Correto", "Correto");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
