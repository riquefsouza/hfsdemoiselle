package br.hfs.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.hfs.business.EntidadeBC;
import br.hfs.domain.Entidade;

@ViewController
@NextView("/private/entidade_edit.jsf")
@PreviousView("/private/entidade_list.jsf")
public class EntidadeListMB extends AbstractListPageBean<Entidade, Integer> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntidadeBC entidadeBC;
	
	@Override
	protected List<Entidade> handleResultList() {
		return this.entidadeBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Integer> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Integer id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				entidadeBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}