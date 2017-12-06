package br.hfs.util.assinatura;

import java.util.logging.Logger;

public class AssinaturaDigitalException extends Exception {
	private static final long serialVersionUID = 1L;

	public AssinaturaDigitalException(String message) {
		super(message);
	}

	public AssinaturaDigitalException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
