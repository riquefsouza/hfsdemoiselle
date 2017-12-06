package br.hfs.util.email;

import java.util.logging.Logger;

public class EmailUtilException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmailUtilException(String message) {
		super(message);
	}

	public EmailUtilException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
