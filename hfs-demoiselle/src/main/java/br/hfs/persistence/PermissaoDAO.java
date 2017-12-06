package br.hfs.persistence;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.hfs.domain.Permissao;

@PersistenceController
public class PermissaoDAO extends JPACrud<Permissao, Integer> {

	private static final long serialVersionUID = 1L;
	

}
