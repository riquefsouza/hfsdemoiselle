package br.hfs.business;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.hfs.domain.Entidade;
import br.hfs.domain.EntidadePerfil;
import br.hfs.domain.EntidadePerfilPK;
import br.hfs.persistence.EntidadePerfilDAO;

@BusinessController
public class EntidadePerfilBC extends
		DelegateCrud<EntidadePerfil, EntidadePerfilPK, EntidadePerfilDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilBC perfilBC;

	@Inject
	private EntidadeBC entidadeBC;

	@Transactional
	public void load() {
		if (findAll().isEmpty()) {
			int perfilId = perfilBC.findAll().get(0).getId().intValue();

			for (Entidade item: entidadeBC.findAll()) {
				insert(new EntidadePerfil(new EntidadePerfilPK(item.getId(),
						perfilId)));
			}

		}
	}
}
