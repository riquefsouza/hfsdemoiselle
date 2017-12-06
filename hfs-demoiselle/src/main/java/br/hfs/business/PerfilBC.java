package br.hfs.business;

import java.util.Date;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.hfs.domain.Perfil;
import br.hfs.persistence.PerfilDAO;

@BusinessController
public class PerfilBC extends DelegateCrud<Perfil, Integer, PerfilDAO> {
	private static final long serialVersionUID = 1L;

	@Transactional
	public void load() {
		if (findAll().isEmpty()) {
			insert(new Perfil("Administrador", null, null, Long.valueOf(1),
					new Date(), null, null));
		}
	}
}
