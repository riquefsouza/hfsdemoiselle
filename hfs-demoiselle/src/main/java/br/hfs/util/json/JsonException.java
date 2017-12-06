package br.hfs.util.json;

import java.util.logging.Logger;

public class JsonException extends Exception {
	private static final long serialVersionUID = 1L;

	public JsonException(String message) {
		super(message);
	}

	public JsonException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
