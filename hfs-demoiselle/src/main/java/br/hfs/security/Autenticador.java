package br.hfs.security;

import java.security.Principal;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.certificate.criptography.DigestAlgorithmEnum;
import br.gov.frameworkdemoiselle.security.AuthenticationException;
import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.hfs.business.UsuarioBC;
import br.hfs.util.security.SegurancaUtil;

@SessionScoped
public class Autenticador implements Authenticator {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBC usuarioBC;

	@Inject
	private Credenciais credenciais;

	@Inject
	private ResourceBundle bundle;
	
	@Inject
	SegurancaUtil segurancaUtil;

	private static boolean authenticated = false;

	@Override
	public void authenticate() throws Exception {

		String login = credenciais.getLogin();
		String senha = segurancaUtil.HashSimplesHex(credenciais.getSenha()
				.getBytes(), DigestAlgorithmEnum.SHA_1);

		if (usuarioBC.logar(login, senha)) {
			credenciais.setUsuario(usuarioBC.autenticado(login, senha));
			authenticated = true;
		} else {
			authenticated = false;
		}

		if (!authenticated) {
			throw new AuthenticationException(
					bundle.getString("usuarioNaoAutenticado"));
		}

	}

	@Override
	public Principal getUser() {
		if (authenticated) {
			return new Principal() {
				@Override
				public String getName() {
					return credenciais.getLogin();
				}
			};
		} else {
			return null;
		}
	}

	@Override
	public void unauthenticate() throws Exception {
		credenciais.clear();
		authenticated = false;
	}
}
