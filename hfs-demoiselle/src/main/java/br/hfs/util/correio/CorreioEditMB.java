package br.hfs.util.correio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("/private/correio_lazylist.xhtml")
public class CorreioEditMB extends AbstractEditPageBean<Correio, Integer> {

	private static final long serialVersionUID = 1L;

	private List<String> listaEstado;
	private List<String> listaCidade;
	private List<String> listaTipoLogradouro;

	@Inject
	private CorreioDAO correioDAO;

	@Inject
	private MessageContext messageContext;

	@PostConstruct
	public void init() {
		try {
			listaEstado = correioDAO.listarEstados();
			listaTipoLogradouro = correioDAO.listarTiposLogradouro();
			onEstadoChange();
		} catch (SQLException e) {
			messageContext.add(e.getMessage());
		}
	}

	@Override
	@Transactional
	public String delete() {
		try {
			this.correioDAO.delete(getId());
		} catch (SQLException e) {
			messageContext.add(e.getMessage());
		}
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		try {
			this.correioDAO.insert(this.getBean());
		} catch (SQLException e) {
			messageContext.add(e.getMessage());
		}
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		try {
			this.correioDAO.update(this.getBean());
		} catch (SQLException e) {
			messageContext.add(e.getMessage());
		}
		return getPreviousView();
	}

	@Override
	protected Correio handleLoad(Integer id) {
		Correio bean = null;
		try {
			bean = this.correioDAO.load(id);
		} catch (SQLException e) {
			messageContext.add(e.getMessage());
		}
		return bean;
	}

	public void selecionarEstado(final ValueChangeEvent event) {
		//this.getBean().setEstado((String) event.getNewValue());
	}
	
	public void onEstadoChange() {
		if (this.getBean().getEstado() != null
				&& !this.getBean().getEstado().equals(""))
			try {
				listaCidade = correioDAO.pesquisarCidadesPorEstado(this.getBean()
						.getEstado());
			} catch (SQLException e) {
				messageContext.add(e.getMessage());
			}
		else
			listaCidade = new ArrayList<String>();
	}

	public void selecionarCidade(final ValueChangeEvent event) {
		//this.getBean().setCidade((String) event.getNewValue());
	}

	public List<String> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<String> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public List<String> getListaTipoLogradouro() {
		return listaTipoLogradouro;
	}

	public void setListaTipoLogradouro(List<String> listaTipoLogradouro) {
		this.listaTipoLogradouro = listaTipoLogradouro;
	}

	public List<String> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<String> listaCidade) {
		this.listaCidade = listaCidade;
	}

}