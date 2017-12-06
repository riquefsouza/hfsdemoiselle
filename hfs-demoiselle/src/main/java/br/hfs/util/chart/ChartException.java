package br.hfs.util.chart;

import java.util.logging.Logger;

public class ChartException extends Exception {
	private static final long serialVersionUID = 1L;

	public ChartException(String message) {
		super(message);
	}

	public ChartException(Logger log, String message) {
		super(message);
		log.severe(message);
	}

}
