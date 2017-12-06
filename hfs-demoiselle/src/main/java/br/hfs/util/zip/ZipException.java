package br.hfs.util.zip;

import java.util.logging.Logger;

public class ZipException extends Exception {
	private static final long serialVersionUID = 1L;

	public ZipException(String message) {
		super(message);
	}

	public ZipException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
