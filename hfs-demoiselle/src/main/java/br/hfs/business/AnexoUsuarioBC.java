package br.hfs.business;

import java.util.List;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.hfs.domain.AnexoUsuario;
import br.hfs.persistence.AnexoUsuarioDAO;

@BusinessController
public class AnexoUsuarioBC extends
		DelegateCrud<AnexoUsuario, Long, AnexoUsuarioDAO> {

	private static final long serialVersionUID = 1L;

	public List<AnexoUsuario> listarAnexos(Long idUsuario) {
		return this.getDelegate().listarAnexos(idUsuario);
	}

}
