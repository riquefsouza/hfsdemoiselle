package br.hfs.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.hfs.business.PermissaoBC;
import br.hfs.domain.Permissao;

@ViewController
@NextView("/private/permissao_edit.jsf")
@PreviousView("/private/permissao_list.jsf")
public class PermissaoListMB extends AbstractListPageBean<Permissao, Integer> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PermissaoBC permissaoBC;
	
	@Override
	protected List<Permissao> handleResultList() {
		return this.permissaoBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Integer> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Integer id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				permissaoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}