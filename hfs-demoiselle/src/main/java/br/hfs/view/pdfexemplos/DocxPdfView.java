package br.hfs.view.pdfexemplos;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.docx.DocxException;
import br.hfs.util.docx.DocxUtil;

@ManagedBean
public class DocxPdfView implements Serializable {
	private static final long serialVersionUID = 1L;

	private UploadedFile arquivoDOCX;

	private StreamedContent saida;

	@Inject
	private DocxUtil docxUtil;

	@Inject
	private MessageContext messageContext;

	public StreamedContent getSaida() {
		if (arquivoDOCX != null && arquivoDOCX.getSize() > 0) {
			try {
				byte[] arquivoPDF = docxUtil.converteDOCXtoPDF(arquivoDOCX
						.getContents());

				saida = new DefaultStreamedContent(
						new ByteArrayInputStream(arquivoPDF),
						"application/pdf",
						arquivoDOCX.getFileName() + ".pdf");
			} catch (DocxException e) {
				messageContext.add(e.getMessage());
			}

		} else {
			messageContext.add("Arquivo DOCX não informado!");
		}

		return saida;
	}

	public UploadedFile getArquivoDOCX() {
		return arquivoDOCX;
	}

	public void setArquivoDOCX(UploadedFile arquivoDOCX) {
		this.arquivoDOCX = arquivoDOCX;
	}

}
