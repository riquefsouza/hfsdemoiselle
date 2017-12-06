package br.hfs.util.tts;

import java.util.logging.Logger;

public class TtsException extends Exception {
	private static final long serialVersionUID = 1L;

	public TtsException(String message) {
		super(message);
	}

	public TtsException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
