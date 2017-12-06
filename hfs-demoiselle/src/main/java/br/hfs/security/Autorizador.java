package br.hfs.security;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.AuthorizationException;
import br.gov.frameworkdemoiselle.security.Authorizer;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.util.ResourceBundle;

@SessionScoped
public class Autorizador implements Authorizer {

	private static final long serialVersionUID = 1L;

	@Inject
	private SecurityContext context;

	@Inject
	private ResourceBundle bundle;

	@Override
	public boolean hasRole(String role) throws Exception {

		boolean authorized = false;
		if (context != null) {
			if (context.isLoggedIn()) {
				try {
					//String usr = context.getUser().getName();
					//if (usr.equals("admin") && role.equals("administrador")) {
						authorized = true;
					//} else {
						//authorized = false;
					//}
				} catch (Exception e) {
					throw new AuthorizationException(
							bundle.getString("usuarioNaoAutenticado"));
				}
			} else {
				throw new AuthorizationException(
						bundle.getString("usuarioNaoAutenticado"));
			}
		} else {
			throw new AuthorizationException(
					bundle.getString("usuarioNaoAutenticado"));
		}
		return authorized;
	}

	@Override
	public boolean hasPermission(String res, String op) throws Exception {

		boolean authorized = false;
		if (context != null) {
			try {
				if (context.isLoggedIn()) {
					//String usr = context.getUser().getName();

					if (context.hasRole("administrador")) {
						authorized = true;
					} else {
						/*
						if (usr.equals("atendente") && res.equals("automovel")
								&& op.equals("insert")) {
							authorized = true;
						}

						if (usr.equals("atendente") && res.equals("automovel")
								&& op.equals("update")) {
							authorized = true;
						}
						*/
					}
				} else {
					throw new AuthorizationException(
							bundle.getString("usuarioNaoAutenticado"));
				}
			} catch (Exception e) {
				throw new AuthorizationException(
						bundle.getString("usuarioNaoAutenticado"));
			}
		} else {
			throw new AuthorizationException(
					bundle.getString("usuarioNaoAutenticado"));
		}

		return authorized;
	}

}