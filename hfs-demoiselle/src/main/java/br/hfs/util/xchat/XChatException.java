package br.hfs.util.xchat;

import java.util.logging.Logger;

public class XChatException extends Exception {
	private static final long serialVersionUID = 1L;

	public XChatException(String message) {
		super(message);
	}

	public XChatException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
