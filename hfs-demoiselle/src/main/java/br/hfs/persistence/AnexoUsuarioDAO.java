package br.hfs.persistence;

import java.util.List;

import javax.persistence.Query;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.hfs.domain.AnexoUsuario;

@PersistenceController
public class AnexoUsuarioDAO extends JPACrud<AnexoUsuario, Long> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<AnexoUsuario> listarAnexos(Long idUsuario) {
		Query query = getEntityManager().createNamedQuery(
				"AnexoUsuario.listarPorId");
		query.setParameter("idUsuario", idUsuario);
		return query.getResultList();
	}

}
