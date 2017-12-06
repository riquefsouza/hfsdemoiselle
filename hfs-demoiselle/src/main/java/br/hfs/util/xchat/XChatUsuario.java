package br.hfs.util.xchat;

import java.io.Serializable;

public class XChatUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;

	private String usuario;

	private String senha;

	public XChatUsuario() {
		super();
	}
	
	public XChatUsuario(String nome, String usuario) {
		this.nome = nome;
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
