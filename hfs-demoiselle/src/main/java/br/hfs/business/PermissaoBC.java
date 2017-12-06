package br.hfs.business;

import java.util.Date;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.hfs.domain.Permissao;
import br.hfs.persistence.PermissaoDAO;

@BusinessController
public class PermissaoBC extends DelegateCrud<Permissao, Integer, PermissaoDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntidadePerfilBC entidadePerfilBC;
	
	@Transactional
	public void load() {		
		if (findAll().isEmpty()) {
			insert(new Permissao("inserir", entidadePerfilBC.findAll(),
					Long.valueOf(1), new Date(), null, null));
			insert(new Permissao("alterar", entidadePerfilBC.findAll(),
					Long.valueOf(1), new Date(), null, null));
		}
	}
}
