package br.hfs.view.exemplos;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

@ManagedBean
public class FileUploadView implements Serializable {
	private static final long serialVersionUID = 1L;

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Sucesso", event.getFile()
				.getFileName() + " foi anexado.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
