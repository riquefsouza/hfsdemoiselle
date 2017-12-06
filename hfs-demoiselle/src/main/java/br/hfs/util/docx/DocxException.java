package br.hfs.util.docx;

import java.util.logging.Logger;

public class DocxException extends Exception {
	private static final long serialVersionUID = 1L;

	public DocxException(String message) {
		super(message);
	}

	public DocxException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
