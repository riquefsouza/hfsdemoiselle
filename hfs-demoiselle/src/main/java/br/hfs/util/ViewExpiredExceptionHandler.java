package br.hfs.util;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class ViewExpiredExceptionHandler extends ExceptionHandlerWrapper {
	private ExceptionHandler wrapped;

	public ViewExpiredExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	public void handle() throws FacesException {
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents()
				.iterator(); i.hasNext();) {
			ExceptionQueuedEvent event = (ExceptionQueuedEvent) i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();
			Throwable t = context.getException();
			if ((t instanceof ViewExpiredException)) {
				ViewExpiredException vee = (ViewExpiredException) t;
				FacesContext fc = FacesContext.getCurrentInstance();
				Map<String, Object> requestMap = fc.getExternalContext()
						.getRequestMap();
				NavigationHandler nav = fc.getApplication()
						.getNavigationHandler();
				try {
					requestMap.put("currentViewId", vee.getViewId());

					nav.handleNavigation(fc, null, "error/viewExpired.jsf");
					fc.renderResponse();
				} finally {
					i.remove();
				}
			}

		}

		getWrapped().handle();
	}
}