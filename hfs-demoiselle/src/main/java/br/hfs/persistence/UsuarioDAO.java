package br.hfs.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.hfs.domain.Usuario;

@PersistenceController
public class UsuarioDAO extends JPACrud<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	/*
	 * private String autenticar(String senha) { TypedQuery<String> query =
	 * getEntityManager().createNamedQuery("Usuario.HadesPalavra",
	 * String.class); query.setParameter("p_senha", senha); return (String)
	 * query.getSingleResult(); }
	 */
	public boolean logar(String login, String senha) {
		// String sSenha = autenticar(senha);

		Query query = getEntityManager().createNamedQuery("Usuario.logar");
		query.setParameter("login", login);
		// query.setParameter("senha", sSenha);
		query.setParameter("senha", senha);

		try {
			Integer res = (Integer) query.getSingleResult();
			return (res.intValue() == 1 ? true : false);
		} catch (NoResultException e) {
			return false;
		}
	}

	public Usuario autenticado(String login, String senha) {
		Query query = getEntityManager()
				.createNamedQuery("Usuario.autenticado");
		query.setParameter("login", login);
		query.setParameter("senha", senha);

		try {
			Usuario res = (Usuario) query.getSingleResult();
			return res;
		} catch (NoResultException e) {
			return null;
		}
	}

	public int contaTodos() {
		long result = (Long) getEntityManager().createNamedQuery(
				"Usuario.total").getSingleResult();

		return (int) result;
	}

	public List<Usuario> listaTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<Usuario> query = getEntityManager().getCriteriaBuilder()
				.createQuery(Usuario.class);
		query.select(query.from(Usuario.class));

		List<Usuario> lista = getEntityManager().createQuery(query)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();

		return lista;
	}

}
