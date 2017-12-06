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
public class PdfDocxView implements Serializable {
	private static final long serialVersionUID = 1L;

	private UploadedFile arquivoPDF;

	private StreamedContent saida;

	@Inject
	private DocxUtil docxUtil;

	@Inject
	private MessageContext messageContext;

	public StreamedContent getSaida() {
		if (arquivoPDF != null && arquivoPDF.getSize() > 0) {
			try {
				byte[] arquivoDOCX = docxUtil.convertePDFtoDOCX(arquivoPDF
						.getContents());

				saida = new DefaultStreamedContent(
						new ByteArrayInputStream(arquivoDOCX),
						"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
						arquivoPDF.getFileName() + ".docx");
			} catch (DocxException e) {
				messageContext.add(e.getMessage());
			}

		} else {
			messageContext.add("Arquivo PDF não informado!");
		}

		return saida;
	}

	public UploadedFile getArquivoPDF() {
		return arquivoPDF;
	}

	public void setArquivoPDF(UploadedFile arquivoPDF) {
		this.arquivoPDF = arquivoPDF;
	}

}
