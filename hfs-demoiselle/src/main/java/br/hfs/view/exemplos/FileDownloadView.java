package br.hfs.view.exemplos;

import java.io.InputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
public class FileDownloadView implements Serializable {
 	private static final long serialVersionUID = 1L;

	private StreamedContent file;

	public FileDownloadView() {
		InputStream stream = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getResourceAsStream("/resources/images/logo.png");
		file = new DefaultStreamedContent(stream, "image/png", "logo.png");
	}

	public StreamedContent getFile() {
		return file;
	}
}