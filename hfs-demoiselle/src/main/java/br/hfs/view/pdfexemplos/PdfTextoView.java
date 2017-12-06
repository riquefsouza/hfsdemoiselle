package br.hfs.view.pdfexemplos;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.pdf.PdfException;
import br.hfs.util.pdf.PdfPagina;
import br.hfs.util.pdf.PdfUtil;

@ManagedBean
public class PdfTextoView implements Serializable {
	private static final long serialVersionUID = 1L;

	private UploadedFile arquivoPDF;
	
	private List<PdfPagina> lista;
	
	@Inject
	private PdfUtil pdfUtil;

	@Inject
	private MessageContext messageContext;

	public void carregar() {
		if (arquivoPDF != null && arquivoPDF.getSize() > 0) {
			try {
				lista = pdfUtil.pdfToTexto(arquivoPDF.getContents());
			} catch (PdfException e) {
				messageContext.add(e.getMessage());
			}

		} else {
			messageContext.add("Arquivo PDF não informado!");
		}
	}

	public List<PdfPagina> getLista() {
		return lista;
	}

	public void setLista(List<PdfPagina> lista) {
		this.lista = lista;
	}

	public UploadedFile getArquivoPDF() {
		return arquivoPDF;
	}

	public void setArquivoPDF(UploadedFile arquivoPDF) {
		this.arquivoPDF = arquivoPDF;
	}

}
