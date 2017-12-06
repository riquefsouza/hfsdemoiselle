package br.hfs.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ViewExpiredExceptionHandlerFactory extends ExceptionHandlerFactory {
	private ExceptionHandlerFactory parent;

	public ViewExpiredExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler result = this.parent.getExceptionHandler();
		result = new ViewExpiredExceptionHandler(result);

		return result;
	}
}