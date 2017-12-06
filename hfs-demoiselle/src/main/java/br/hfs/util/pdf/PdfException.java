package br.hfs.util.pdf;

import java.util.logging.Logger;

public class PdfException extends Exception {
	private static final long serialVersionUID = 1L;

	public PdfException(String message) {
		super(message);
	}

	public PdfException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
