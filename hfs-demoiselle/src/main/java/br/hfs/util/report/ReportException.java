package br.hfs.util.report;

import java.util.logging.Logger;

public class ReportException extends Exception {
	private static final long serialVersionUID = 1L;

	public ReportException(String message) {
		super(message);
	}

	public ReportException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
