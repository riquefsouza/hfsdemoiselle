package br.hfs.util.correio;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
@NextView("/private/correio_edit.xhtml")
@PreviousView("/private/correio_lazylist.xhtml")
public class CorreioLazyView implements Serializable {
	private static final long serialVersionUID = 1L;

	private LazyDataModel<Correio> lazyModel;

	private Correio selectedCorreio;

	private int totalLinhas = 0;

	private int numeroPagina = 0;

	private int tamanhoPagina = 10;

	private String nextView;

	private String previousView;

	@Inject
	private MessageContext messageContext;

	@Inject
	private CorreioDAO dao;

	@PostConstruct
	public void init() {
		try {
			totalLinhas = dao.contaTotal();
			lazyModel = new CorreioLazyDataModel(totalLinhas);
		} catch (SQLException e) {
			messageContext.add(e.getMessage());
		}
	}

	public void clear() {
		totalLinhas = 0;
		lazyModel = null;
	}

	public String getNextView() {
		if (nextView == null) {
			NextView annotation = this.getClass().getAnnotation(NextView.class);

			if (annotation != null) {
				nextView = annotation.value();
			}
		}

		return nextView;
	}

	public String getPreviousView() {
		if (previousView == null) {
			PreviousView annotation = this.getClass().getAnnotation(
					PreviousView.class);

			if (annotation != null) {
				previousView = annotation.value();
			}
		}

		return previousView;
	}

	public LazyDataModel<Correio> getLazyModel() {
		return lazyModel;
	}

	public Correio getSelectedCorreio() {
		return selectedCorreio;
	}

	public void setSelectedCorreio(Correio selectedCorreio) {
		this.selectedCorreio = selectedCorreio;
	}

	public void onRowSelect(SelectEvent event) {
		Correio correio = (Correio) event.getObject();
		messageContext.add("Correio Selecionado: " + correio.toString());
	}

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public int getTamanhoPagina() {
		return tamanhoPagina;
	}

	public int getTotalLinhas() {
		return totalLinhas;
	}

	public void setTotalLinhas(int totalLinhas) {
		this.totalLinhas = totalLinhas;
	}

}
