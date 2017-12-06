package br.hfs.util.template;

import java.util.HashMap;
import java.util.Map;

public class TemplateExemplo {

	public static void main(String[] args) {
		TemplateUtil tu = new TemplateUtil();
		String caminho = "C:/Demoiselle/workspace/hfs-demoiselle/src/main/webapp/private/template";

		try {
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("nomeUsuario", "Henrique Figueiredo de Souza");
			parametros.put("novaSenha", "1234567890");
			parametros.put("anoAtual", "2016");

			System.out.println(tu.templateToString(caminho, "emailAlteracaoSenha.html", parametros));
		} catch (TemplateUtilException e) {
			e.printStackTrace();
		}

	}

}
