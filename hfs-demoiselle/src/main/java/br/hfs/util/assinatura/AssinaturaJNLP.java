package br.hfs.util.assinatura;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class AssinaturaJNLP implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getJNLP() {
		return montarJNLP("hfsAssinador.jnlp", "Assinador Digital",
				"Henrique Figueiredo de Souza", "HFS Assinador Digital",
				"1.7+", new String[] { "assinador-jnlp.jar", "javaws.jar", "commons-io-2.5.jar" },
				null, "br.hfs.util.assinatura.AssinaturaFrameJNLP");
	}

	private String montarJNLP(String nomeJNLP, String titulo,
			String fornecedor, String descricao, String javaVersao,
			String[] jars, Map<String, String> params, String classePrincipal) {
		FacesContext ctxt = FacesContext.getCurrentInstance();
		ExternalContext ext = ctxt.getExternalContext();
		HttpServletRequest req = (HttpServletRequest) ext.getRequest();
		//String url = req.getRequestURL().toString();
		// return url.substring(0, url.length() - req.getRequestURI().length())
		// + req.getContextPath() + "/";
		String url = getURL(req, true);
		String homepage = getURL(req, false);

		/*
		 * Map<String, String> params = FacesContext.getCurrentInstance()
		 * .getExternalContext().getRequestParameterMap(); String parameterOne =
		 * params.get("parameterOne");
		 */

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		sb.append("<jnlp spec=\"1.0+\" codebase=\"").append(url)
				.append("\" href=\"").append(nomeJNLP).append("\">\n");
		sb.append("\t<information>\n");
		sb.append("\t\t<title>").append(titulo).append("</title>\n");
		sb.append("\t\t<vendor>").append(fornecedor).append("</vendor>\n");
		sb.append("\t\t<description>").append(descricao).append("</description>\n");
		sb.append("\t\t<homepage href=\"").append(homepage).append("\"/>\n");
		sb.append("\t</information>\n");
		sb.append("\t<security>\n");
		sb.append("\t\t<all-permissions/>\n");
		sb.append("\t</security>\n");
		sb.append("\t<resources>\n");
		sb.append("\t\t<j2se version=\"").append(javaVersao).append("\"/>\n");

		for (int i = 0; i < jars.length; i++) {
			if (i == 0)
				sb.append("\t\t<jar href=\"").append(jars[i])
						.append("\" main=\"true\"/>\n");
			else
				sb.append("\t\t<jar href=\"").append(jars[i]).append("\"/>\n");
		}

		if (params!=null) {
			Set<Map.Entry<String, String>> conjunto = params.entrySet();
			
			for (Map.Entry<String, String> item : conjunto) {
				sb.append("\t\t<property name=\"")
						.append(item.getKey()).append("\" value=\"")
						.append(item.getValue()).append("\"/>\n");
			}
		}

		sb.append("\t</resources>\n");
		sb.append("\t<application-desc main-class=\"").append(classePrincipal)
				.append("\">\n");
		sb.append("\t\t<argument>").append(url)
				.append("</argument>\n");
        sb.append("\t</application-desc>\n");
		
		sb.append("</jnlp>");

		return sb.toString();
	}

	public String getURL(HttpServletRequest req, boolean codebase) {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo();
		String queryString = req.getQueryString();

		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if (serverPort != 80 && serverPort != 443) {
			url.append(":").append(serverPort);
		}

		if (codebase) {
			url.append(contextPath);
		} else {		
			url.append(contextPath).append(servletPath);
	
			if (pathInfo != null) {
				url.append(pathInfo);
			}
			if (queryString != null) {
				url.append("?").append(queryString);
			}
		}
		return url.toString();
	}

}
