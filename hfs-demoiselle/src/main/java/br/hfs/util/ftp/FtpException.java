package br.hfs.util.ftp;

import java.util.logging.Logger;

public class FtpException extends Exception {
	private static final long serialVersionUID = 1L;

	public FtpException(String message) {
		super(message);
	}

	public FtpException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
