package br.hfs.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.hfs.business.PerfilBC;
import br.hfs.domain.Perfil;

@ViewController
@NextView("/private/perfil_edit.jsf")
@PreviousView("/private/perfil_list.jsf")
public class PerfilListMB extends AbstractListPageBean<Perfil, Integer> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilBC perfilBC;
	
	@Override
	protected List<Perfil> handleResultList() {
		return this.perfilBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Integer> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Integer id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				perfilBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}