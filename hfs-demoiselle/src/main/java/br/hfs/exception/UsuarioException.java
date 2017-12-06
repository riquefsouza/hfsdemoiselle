package br.hfs.exception;

import br.gov.frameworkdemoiselle.exception.ApplicationException;

@ApplicationException(rollback = true)
public class UsuarioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UsuarioException() {
		super("Erro de usuario!");
	}
}
