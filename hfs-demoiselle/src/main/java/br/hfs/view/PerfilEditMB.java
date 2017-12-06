package br.hfs.view;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.hfs.audit.Auditoria;
import br.hfs.business.EntidadeBC;
import br.hfs.business.PerfilBC;
import br.hfs.business.UsuarioBC;
import br.hfs.domain.Entidade;
import br.hfs.domain.Perfil;
import br.hfs.domain.Usuario;

@ViewController
@PreviousView("/private/perfil_list.jsf")
public class PerfilEditMB extends AbstractEditPageBean<Perfil, Integer> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilBC perfilBC;

	private DualListModel<Entidade> entidadeList;

	@Inject
	private EntidadeBC entidadeBC;

	@Inject
	private Auditoria auditoria;
	
	public void setEntidadeList(DualListModel<Entidade> entidadeList) {
		this.entidadeList = entidadeList;
	}

	public void addEntidadeList(List<Entidade> entidadeList) {
		this.getBean().getEntidades().addAll(entidadeList);
	}

	public void deleteEntidadeList(List<Entidade> entidadeList) {
		this.getBean().getEntidades().removeAll(entidadeList);
	}

	public DualListModel<Entidade> getEntidadeList() {
		if (this.entidadeList == null) {
			List<Entidade> source = entidadeBC.findAll();
			List<Entidade> target = this.getBean().getEntidades();

			if (source == null) {
				source = new ArrayList<Entidade>();
			}
			if (target == null) {
				target = new ArrayList<Entidade>();
			} else {
				source.removeAll(target);
			}
			this.entidadeList = new DualListModel<Entidade>(source, target);

		}
		return this.entidadeList;
	}

	private DualListModel<Usuario> usuarioList;

	@Inject
	private UsuarioBC usuarioBC;

	public void setUsuarioList(DualListModel<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public void addUsuarioList(List<Usuario> usuarioList) {
		this.getBean().getUsuarios().addAll(usuarioList);
	}

	public void deleteUsuarioList(List<Usuario> usuarioList) {
		this.getBean().getUsuarios().removeAll(usuarioList);
	}

	public DualListModel<Usuario> getUsuarioList() {
		if (this.usuarioList == null) {
			List<Usuario> source = usuarioBC.findAll();
			List<Usuario> target = this.getBean().getUsuarios();

			if (source == null) {
				source = new ArrayList<Usuario>();
			}
			if (target == null) {
				target = new ArrayList<Usuario>();
			} else {
				source.removeAll(target);
			}
			this.usuarioList = new DualListModel<Usuario>(source, target);

		}
		return this.usuarioList;
	}

	@SuppressWarnings("unchecked")
	public void onTransferEntidade(TransferEvent event) {
		if (event.isAdd()) {
			this.addEntidadeList((List<Entidade>) event.getItems());
		}
		if (event.isRemove()) {
			this.deleteEntidadeList((List<Entidade>) event.getItems());
		}
	}

	@SuppressWarnings("unchecked")
	public void onTransferUsuario(TransferEvent event) {
		if (event.isAdd()) {
			this.addUsuarioList((List<Usuario>) event.getItems());
		}
		if (event.isRemove()) {
			this.deleteUsuarioList((List<Usuario>) event.getItems());
		}
	}
	
	@Override
	@Transactional
	public String delete() {
		this.perfilBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		Perfil bean = this.getBean();	
		bean = (Perfil) auditoria.setAuditoria(bean, true);			
		
		this.perfilBC.insert(bean);
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		Perfil bean = this.getBean();	
		bean = (Perfil) auditoria.setAuditoria(bean, false);	
		
		this.perfilBC.update(bean);
		return getPreviousView();
	}

	@Override
	protected Perfil handleLoad(Integer id) {
		return this.perfilBC.load(id);
	}
}