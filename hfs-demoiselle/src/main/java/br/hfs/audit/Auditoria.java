package br.hfs.audit;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.hfs.security.Credenciais;

public class Auditoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	Credenciais credenciais;
	
	public Auditoria() {
		super();
	}

	public IAuditoria setAuditoria(IAuditoria obj, boolean bInsert) {
		if (bInsert) {
			obj.setIdUsuarioInclusao(credenciais.getUsuario().getId());
			obj.setDataInclusao(new Date());
			obj.setIdUsuarioAlteracao(null);
			obj.setDataAlteracao(null);
		} else {
			obj.setIdUsuarioAlteracao(credenciais.getUsuario().getId());
			obj.setDataAlteracao(new Date());
		}
		return obj;
	}
}
