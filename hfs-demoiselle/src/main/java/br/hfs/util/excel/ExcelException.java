package br.hfs.util.excel;

import java.util.logging.Logger;

public class ExcelException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExcelException(String message) {
		super(message);
	}

	public ExcelException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
