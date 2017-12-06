package br.hfs.view.pdfexemplos;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.odt.OdtException;
import br.hfs.util.odt.OdtUtil;

@ManagedBean
public class OdtPdfView implements Serializable {
	private static final long serialVersionUID = 1L;

	private UploadedFile arquivoODT;

	private StreamedContent saida;

	@Inject
	private OdtUtil odtUtil;

	@Inject
	private MessageContext messageContext;

	public StreamedContent getSaida() {
		if (arquivoODT != null && arquivoODT.getSize() > 0) {
			try {
				byte[] arquivoPDF = odtUtil.converteODTtoPDF(arquivoODT
						.getContents());

				saida = new DefaultStreamedContent(new ByteArrayInputStream(
						arquivoPDF), "application/pdf",
						arquivoODT.getFileName() + ".pdf");
			} catch (OdtException e) {
				messageContext.add(e.getMessage());
			}

		} else {
			messageContext.add("Arquivo ODT não informado!");
		}

		return saida;
	}

	public UploadedFile getArquivoODT() {
		return arquivoODT;
	}

	public void setArquivoODT(UploadedFile arquivoODT) {
		this.arquivoODT = arquivoODT;
	}

}
