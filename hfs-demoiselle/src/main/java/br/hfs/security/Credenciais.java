package br.hfs.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.hfs.domain.Usuario;

@SessionScoped
@Named("credential")
public class Credenciais implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;

	private String senha;
	
	private Usuario usuario;

	public void clear() {
		login = null;
		senha = null;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
