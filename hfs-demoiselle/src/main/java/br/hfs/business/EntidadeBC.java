package br.hfs.business;

import java.util.Date;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.hfs.domain.Entidade;
import br.hfs.persistence.EntidadeDAO;

@BusinessController
public class EntidadeBC extends DelegateCrud<Entidade, Integer, EntidadeDAO> {
	private static final long serialVersionUID = 1L;

	@Transactional
	public void load() {
		if (findAll().isEmpty()) {
			String[] valores = new String[] { "usuario", "perfil", "permissao",
					"entidade", "propsistema", "lazy", "ldap", "feriado",
					"extenso", "download", "chart", "upload", "editor",
					"alterar_senha" };

			for (int i = 0; i < valores.length; i++) {
				insert(new Entidade(valores[i], null, null, Long.valueOf(1),
						new Date(), null, null));
			}
		}
	}
}
