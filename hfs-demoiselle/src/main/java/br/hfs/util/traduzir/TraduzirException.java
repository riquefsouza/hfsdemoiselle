package br.hfs.util.traduzir;

import java.util.logging.Logger;

public class TraduzirException extends Exception {
	private static final long serialVersionUID = 1L;

	public TraduzirException(String message) {
		super(message);
	}

	public TraduzirException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
