package br.hfs.util.certificado;

import java.util.logging.Logger;

public class CertificadoException extends Exception {
	private static final long serialVersionUID = 1L;

	public CertificadoException(String message) {
		super(message);
	}

	public CertificadoException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
