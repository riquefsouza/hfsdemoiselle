package br.hfs.util.odt;

import java.util.logging.Logger;

public class OdtException extends Exception {
	private static final long serialVersionUID = 1L;

	public OdtException(String message) {
		super(message);
	}

	public OdtException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
