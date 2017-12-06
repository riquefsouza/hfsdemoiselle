package br.hfs.util.sftp;

import java.util.logging.Logger;

public class SFtpException extends Exception {
	private static final long serialVersionUID = 1L;

	public SFtpException(String message) {
		super(message);
	}

	public SFtpException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
