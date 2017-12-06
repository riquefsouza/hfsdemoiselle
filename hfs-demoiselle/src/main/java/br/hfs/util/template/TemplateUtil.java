package br.hfs.util.template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.Map;
import java.util.logging.Logger;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("TemplateUtil");

	public byte[] templateToByteArray(String caminho, String template,
			Map<String, String> parametros) throws TemplateUtilException {
		return montarTemplate(caminho, template, parametros).toByteArray();
	}

	public String templateToString(String caminho, String template,
			Map<String, String> parametros) throws TemplateUtilException {
		return montarTemplate(caminho, template, parametros).toString();
	}

	private ByteArrayOutputStream montarTemplate(String caminho,
			String template, Map<String, String> parametros)
			throws TemplateUtilException {

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		try {
			Configuration configuracao = new Configuration();
			configuracao.setDirectoryForTemplateLoading(new File(caminho));
			configuracao.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);

			Template templateModelo = configuracao.getTemplate(template);

			Writer out = new OutputStreamWriter(byteOut);
			templateModelo.process(parametros, out);
			out.flush();

		} catch (IOException e) {
			throw new TemplateUtilException(log, e.getMessage());
		} catch (TemplateException e) {
			throw new TemplateUtilException(log, e.getMessage());
		}

		return byteOut;
	}

}
