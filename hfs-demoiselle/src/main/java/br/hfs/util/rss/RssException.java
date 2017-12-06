package br.hfs.util.rss;

import java.util.logging.Logger;

public class RssException extends Exception {
	private static final long serialVersionUID = 1L;

	public RssException(String message) {
		super(message);
	}

	public RssException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
