package br.hfs.util;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.hfs.AplicacaoConfig;

public class SessionListener implements HttpSessionListener {
	private int sessionCount = 0;

	@Inject
	Logger logger;

	@Inject
	private AplicacaoConfig config;

	public void sessionCreated(HttpSessionEvent event) {
		if (config.isVerLogSessao()) {

			synchronized (this) {
				sessionCount++;
			}
			logger.info("Sessao Criada: " + event.getSession().getId());
			logger.info("Total de Sessoes: " + sessionCount);
		}
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		if (config.isVerLogSessao()) {

			synchronized (this) {
				sessionCount--;
			}
			logger.info("Sessao Destruida: " + event.getSession().getId());
			logger.info("Total de Sessoes: " + sessionCount);
		}
	}

}