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
import br.hfs.business.EntidadePerfilBC;
import br.hfs.business.PermissaoBC;
import br.hfs.domain.EntidadePerfil;
import br.hfs.domain.Permissao;

@ViewController
@PreviousView("/private/permissao_list.jsf")
public class PermissaoEditMB extends AbstractEditPageBean<Permissao, Integer> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PermissaoBC permissaoBC;

	private DualListModel<EntidadePerfil> entidadePerfilList;

	@Inject
	private EntidadePerfilBC entidadePerfilBC;

	@Inject
	private Auditoria auditoria;
	
	public void setEntidadePerfilList(
			DualListModel<EntidadePerfil> entidadePerfilList) {
		this.entidadePerfilList = entidadePerfilList;
	}

	public void addEntidadePerfilList(List<EntidadePerfil> entidadePerfilList) {
		this.getBean().getEntidadesPerfis().addAll(entidadePerfilList);
	}

	public void deleteEntidadePerfilList(List<EntidadePerfil> entidadePerfilList) {
		this.getBean().getEntidadesPerfis().removeAll(entidadePerfilList);
	}

	public DualListModel<EntidadePerfil> getEntidadePerfilList() {
		if (this.entidadePerfilList == null) {
			List<EntidadePerfil> source = entidadePerfilBC.findAll();
			List<EntidadePerfil> target = this.getBean().getEntidadesPerfis();

			if (source == null) {
				source = new ArrayList<EntidadePerfil>();
			}
			if (target == null) {
				target = new ArrayList<EntidadePerfil>();
			} else {
				source.removeAll(target);
			}
			this.entidadePerfilList = new DualListModel<EntidadePerfil>(source,
					target);

		}
		return this.entidadePerfilList;
	}

	@SuppressWarnings("unchecked")
	public void onTransfer(TransferEvent event) {
		if (event.isAdd()) {
			this.addEntidadePerfilList((List<EntidadePerfil>) event.getItems());
		}
		if (event.isRemove()) {
			this.deleteEntidadePerfilList((List<EntidadePerfil>) event
					.getItems());
		}
	}

	@Override
	@Transactional
	public String delete() {
		this.permissaoBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		Permissao bean = this.getBean();	
		bean = (Permissao) auditoria.setAuditoria(bean, true);		
		
		this.permissaoBC.insert(bean);
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		Permissao bean = this.getBean();	
		bean = (Permissao) auditoria.setAuditoria(bean, false);
		
		this.permissaoBC.update(bean);
		return getPreviousView();
	}

	@Override
	protected Permissao handleLoad(Integer id) {
		return this.permissaoBC.load(id);
	}
}