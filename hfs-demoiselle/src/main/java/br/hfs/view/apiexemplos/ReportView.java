package br.hfs.view.apiexemplos;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.report.ReportUtil.FormatoArquivo;
import br.hfs.util.report.ReportUtil.OpcaoGeracao;

@ManagedBean
public class ReportView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String caminho;

	@Inject
	private MessageContext messageContext;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ec = context.getExternalContext();
		caminho = ec.getRequestScheme()
				+ "://"
				+ ec.getRequestServerName()
				+ ":"
				+ ec.getRequestServerPort()
				+ ec.getRequestContextPath()
				+ "/relatorio"
				+ params("listarUsuarios", OpcaoGeracao.PADRAO,
						FormatoArquivo.PDF, false);
	}

	public void gerar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ec = context.getExternalContext();
		try {
			// context.getExternalContext().dispatch(caminho);

			String encodeURL = ec.encodeResourceURL(caminho);
			context.getExternalContext().redirect(encodeURL);
		} catch (IOException e) {
			messageContext.add("ERRO: " + e.getMessage());
		} finally {
			context.responseComplete();
		}

	}

	private String params(String nomeRelatorio, OpcaoGeracao opcao,
			FormatoArquivo formato, boolean compactado) {
		return "?opcao=" + opcao + "&nome=" + nomeRelatorio + "&formato="
				+ formato + "&compactado=" + compactado;
	}

	public String getCaminho() {
		return caminho;
	}

}
