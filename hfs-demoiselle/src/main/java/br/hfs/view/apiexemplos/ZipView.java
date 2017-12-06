package br.hfs.view.apiexemplos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.zip.ZipException;
import br.hfs.util.zip.ZipUtil;

@ManagedBean
public class ZipView implements Serializable {
	private static final long serialVersionUID = 1L;

	private UploadedFile arquivo;

	@Inject
	private ZipUtil zipUtil;

	@Inject
	private MessageContext messageContext;

	public void baixar() {
		if (arquivo != null && arquivo.getSize() > 0) {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();
			try {
				String caminho = ec.getRealPath("WEB-INF");
				String entrada = caminho + "/" + arquivo.getFileName();
				String nomeArquivoZip = caminho + "/" + arquivo.getFileName()
						+ ".zip";
				List<String> arquivos = new ArrayList<String>();
				arquivos.add(arquivo.getFileName());

				File fentrada = new File(entrada);
				FileUtils.writeByteArrayToFile(fentrada,
						arquivo.getContents());

				zipUtil.criarZIP(nomeArquivoZip, caminho, arquivos);

				File fsaida = new File(nomeArquivoZip);
				byte[] conteudo = FileUtils.readFileToByteArray(fsaida);

				ec.responseReset();
				ec.setResponseContentType("application/zip");
				ec.setResponseContentLength(conteudo.length);
				ec.setResponseHeader("Content-Disposition",
						"attachment; filename=\"" + arquivo.getFileName()
								+ ".zip\"");

				OutputStream saida = ec.getResponseOutputStream();
				Streams.copy(new ByteArrayInputStream(conteudo), saida, false);
				
				if (fentrada.exists()){
					fentrada.delete();
				}
					
				if (fsaida.exists()){
					fsaida.delete();
				}
			} catch (IOException e) {
				messageContext.add("ERRO: " + e.getMessage());
			} catch (ZipException e) {
				messageContext.add("ERRO: " + e.getMessage());
			} finally {
				context.responseComplete();
			}

		} else {
			messageContext.add("Arquivo não selecionado!");
		}

	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

}
