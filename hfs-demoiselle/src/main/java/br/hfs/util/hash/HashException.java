package br.hfs.util.hash;

import java.util.logging.Logger;

public class HashException extends Exception {
	private static final long serialVersionUID = 1L;

	public HashException(String message) {
		super(message);
	}

	public HashException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
