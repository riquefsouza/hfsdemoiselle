package br.hfs.domain.lazydatamodel;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.hfs.domain.Usuario;
import br.hfs.persistence.UsuarioDAO;

@ViewController
public class UsuarioLazyView implements Serializable {
	private static final long serialVersionUID = 1L;

	private LazyDataModel<Usuario> lazyModel;

	private Usuario selectedUsuario;

	private int totalLinhas;
	
	@Inject
	private MessageContext messageContext;

	@Inject
	private UsuarioDAO dao;

	@PostConstruct
	public void init() {
		totalLinhas = dao.contaTodos();
		lazyModel = new UsuarioLazyDataModel(dao.findAll());
	}

	public LazyDataModel<Usuario> getLazyModel() {
		return lazyModel;
	}

	public Usuario getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(Usuario selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}

	public void onRowSelect(SelectEvent event) {
		Usuario usuario = (Usuario) event.getObject();
		messageContext.add("Usuario Selecionado: " + usuario.getNome());
	}

	public int getTotalLinhas() {
		return totalLinhas;
	}

	public void setTotalLinhas(int totalLinhas) {
		this.totalLinhas = totalLinhas;
	}
}
