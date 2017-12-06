package br.hfs.util;

import java.util.logging.Logger;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LifeCycleListener implements PhaseListener {
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger("LifeCycleListener");

	public LifeCycleListener() {
		super();
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		logger.fine("INICIANDO FASE: " + event.getPhaseId());
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		logger.fine("FINALIZANDO FASE: " + event.getPhaseId());
	}
}
