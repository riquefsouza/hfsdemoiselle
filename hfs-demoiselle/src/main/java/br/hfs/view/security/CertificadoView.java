package br.hfs.view.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.certificado.CertificadoException;
import br.hfs.util.certificado.CertificadoUtil;
import br.hfs.util.certificado.CertificadoX509;
import br.hfs.util.security.CertificadoDigitalVO;

@ManagedBean
public class CertificadoView implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<CertificadoDigitalVO> lista;

	private UploadedFile arquivo;

	@Inject
	CertificadoUtil certificadoUtil;	

	@Inject
	private MessageContext messageContext;

	public List<CertificadoDigitalVO> getLista() {
		return lista;
	}

	public void gerar() {
		if (arquivo != null && arquivo.getSize() > 0) {
			lista = new ArrayList<CertificadoDigitalVO>();
			try {
				CertificadoX509 cx = certificadoUtil.getCertificadoX509(arquivo
						.getContents());
				lista = cx.toLista();
				Collections.sort(lista);				
			} catch (CertificadoException e) {
				messageContext.add(e.getMessage());
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
