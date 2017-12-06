package br.hfs.util.criptografia;

import java.util.logging.Logger;

public class CriptografiaException extends Exception {
	private static final long serialVersionUID = 1L;

	public CriptografiaException(String message) {
		super(message);
	}

	public CriptografiaException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
