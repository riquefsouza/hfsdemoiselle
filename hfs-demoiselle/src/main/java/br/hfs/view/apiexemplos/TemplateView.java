package br.hfs.view.apiexemplos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.template.TemplateUtil;
import br.hfs.util.template.TemplateUtilException;

@ManagedBean
public class TemplateView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nomeUsuario;

	private String senha;

	private String anoAtual;

	private String resultado;

	@Inject
	private TemplateUtil templateUtil;

	@Inject
	private MessageContext messageContext;

	@PostConstruct
	public void init() {
		nomeUsuario = "JOÃO SEM NOME";
		senha = "1234567890";
		anoAtual = "2016";
		resultado = "";
	}

	public void carregar() {
		if (!nomeUsuario.isEmpty() && !senha.isEmpty() && !anoAtual.isEmpty()) {

			try {
				String caminho = "/private/template";
				caminho = FacesContext.getCurrentInstance()
						.getExternalContext().getRealPath(caminho);

				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put("nomeUsuario", nomeUsuario);
				parametros.put("novaSenha", senha);
				parametros.put("anoAtual", anoAtual);

				resultado = templateUtil.templateToString(caminho,
						"emailAlteracaoSenha.html", parametros);
			} catch (TemplateUtilException e) {
				messageContext.add(e.getMessage());
			}

		} else {
			messageContext.add("Os parâmetros não podem ser vazios!");
		}
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getAnoAtual() {
		return anoAtual;
	}

	public void setAnoAtual(String anoAtual) {
		this.anoAtual = anoAtual;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
}
