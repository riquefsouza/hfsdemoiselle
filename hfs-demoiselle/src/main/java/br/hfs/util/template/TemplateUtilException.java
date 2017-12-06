package br.hfs.util.template;

import java.util.logging.Logger;

public class TemplateUtilException extends Exception {
	private static final long serialVersionUID = 1L;

	public TemplateUtilException(String message) {
		super(message);
	}

	public TemplateUtilException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
