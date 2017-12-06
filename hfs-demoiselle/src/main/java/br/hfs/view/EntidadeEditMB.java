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
import br.hfs.domain.Entidade;
import br.hfs.domain.Perfil;

@ViewController
@PreviousView("/private/entidade_list.jsf")
public class EntidadeEditMB extends AbstractEditPageBean<Entidade, Integer> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntidadeBC entidadeBC;
	
	private DualListModel<Perfil> perfilList;

	@Inject
	private PerfilBC perfilBC;

	@Inject
	private Auditoria auditoria;
	
	public void setPerfilList(DualListModel<Perfil> perfilList) {
		this.perfilList = perfilList;
	}

	public void addPerfilList(List<Perfil> perfilList) {
		this.getBean().getPerfis().addAll(perfilList);
	}

	public void deletePerfilList(List<Perfil> perfilList) {
		this.getBean().getPerfis().removeAll(perfilList);
	}

	public DualListModel<Perfil> getPerfilList() {
		if (this.perfilList == null) {
			List<Perfil> source = perfilBC.findAll();
			List<Perfil> target = this.getBean().getPerfis();

			if (source == null) {
				source = new ArrayList<Perfil>();
			}
			if (target == null) {
				target = new ArrayList<Perfil>();
			} else {
				source.removeAll(target);
			}
			this.perfilList = new DualListModel<Perfil>(source, target);

		}
		return this.perfilList;
	}

	@SuppressWarnings("unchecked")
	public void onTransfer(TransferEvent event) {
		if (event.isAdd()) {
			this.addPerfilList((List<Perfil>) event.getItems());
		}
		if (event.isRemove()) {
			this.deletePerfilList((List<Perfil>) event.getItems());
		}
	}

	@Override
	@Transactional
	public String delete() {
		this.entidadeBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		Entidade bean = this.getBean();	
		bean = (Entidade) auditoria.setAuditoria(bean, true);		
		
		this.entidadeBC.insert(bean);
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		Entidade bean = this.getBean();	
		bean = (Entidade) auditoria.setAuditoria(bean, false);	
		
		this.entidadeBC.update(bean);
		return getPreviousView();
	}

	@Override
	protected Entidade handleLoad(Integer id) {
		return this.entidadeBC.load(id);
	}
}