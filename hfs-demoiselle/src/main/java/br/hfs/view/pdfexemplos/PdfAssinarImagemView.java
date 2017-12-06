package br.hfs.view.pdfexemplos;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.pdf.PdfException;
import br.hfs.util.pdf.PdfUtil;

@ManagedBean
public class PdfAssinarImagemView implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String tipo = "jks";
	
	private UploadedFile arquivoStore;
	
	private UploadedFile arquivoPDF;

	private StreamedContent saida;
	
	private String senha;

	@Inject
	private PdfUtil pdfUtil;

	@Inject
	private MessageContext messageContext;

	public StreamedContent getSaida() {
		if (arquivoStore != null && arquivoStore.getSize() > 0) {
		
			if (arquivoPDF != null && arquivoPDF.getSize() > 0) {
				try {
					boolean btipo = tipo.equals("jks") ? true : false;
					
					byte[] PDFAssinado = pdfUtil.criarAssinaturaVisivel(btipo,
							new ByteArrayInputStream(arquivoStore.getContents()), senha,
							new ByteArrayInputStream(arquivoPDF.getContents()), null, null);
	
					saida = new DefaultStreamedContent(new ByteArrayInputStream(
							PDFAssinado), "application/pdf",
							arquivoPDF.getFileName() + "_assinado.pdf");
	
				} catch (PdfException e) {
					messageContext.add("ERRO: " + e.getMessage());
				}
	
			} else {
				messageContext.add("Arquivo a ser assinado não selecionado!");
			}
			
		} else {
			messageContext.add("Arquivo Java Key Store (.jks) ou PKCS #12 (.pfx) não selecionado!");
		}
		
		return saida;
	}

	public UploadedFile getArquivoStore() {
		return arquivoStore;
	}

	public void setArquivoStore(UploadedFile arquivoStore) {
		this.arquivoStore = arquivoStore;
	}

	public UploadedFile getArquivoPDF() {
		return arquivoPDF;
	}

	public void setArquivoPDF(UploadedFile arquivoPDF) {
		this.arquivoPDF = arquivoPDF;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
