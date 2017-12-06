package br.hfs.persistence;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.hfs.domain.Entidade;

@PersistenceController
public class EntidadeDAO extends JPACrud<Entidade, Integer> {

	private static final long serialVersionUID = 1L;
	

}
