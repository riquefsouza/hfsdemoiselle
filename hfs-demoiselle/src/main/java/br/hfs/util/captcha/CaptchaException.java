package br.hfs.util.captcha;

import java.util.logging.Logger;

public class CaptchaException extends Exception {
	private static final long serialVersionUID = 1L;

	public CaptchaException(String message) {
		super(message);
	}

	public CaptchaException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
