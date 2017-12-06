package br.hfs.util.scanner;

import java.util.logging.Logger;

public class ScannerException extends Exception {
	private static final long serialVersionUID = 1L;

	public ScannerException(String message) {
		super(message);
	}

	public ScannerException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
