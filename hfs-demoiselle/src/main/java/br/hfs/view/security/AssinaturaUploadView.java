package br.hfs.view.security;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.StringUtil;

@ManagedBean
public class AssinaturaUploadView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private MessageContext messageContext;
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
			String nomeArquivo = StringUtil
					.removeDir(event.getFile().getFileName());
			byte[] conteudo = event.getFile().getContents();
			
			String caminho = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/temp");
			
			File arquivo = new File(caminho + File.separatorChar + nomeArquivo);
			
			FileUtils.writeByteArrayToFile(arquivo, conteudo);
		} catch (IOException e) {
			messageContext.add(e.getMessage());
		}
	}

}
