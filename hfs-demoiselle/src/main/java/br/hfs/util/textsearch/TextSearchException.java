package br.hfs.util.textsearch;

import java.util.logging.Logger;

public class TextSearchException extends Exception {
	private static final long serialVersionUID = 1L;

	public TextSearchException(String message) {
		super(message);
	}

	public TextSearchException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
