package br.hfs.util.email;

import java.io.Serializable;

public class EmailAnexo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;
	private String url;

	// private String caminho;

	public EmailAnexo() {
		super();
	}

	public EmailAnexo(String nome, String descricao, String url) {
		this.nome = nome;
		this.descricao = descricao;
		this.url = url;
		// this.caminho = "";
	}

	// public EmailAnexo(String nome, String descricao, String caminho) {
	// this.nome = nome;
	// this.descricao = descricao;
	// this.url = "";
	// this.caminho = caminho;
	// }

	public EmailAnexo(String nome, String url) {
		this.nome = nome;
		this.descricao = nome;
		this.url = url;
		// this.caminho = "";
	}

	// public EmailAnexo(String nome, String caminho) {
	// this.nome = nome;
	// this.descricao = nome;
	// this.url = "";
	// this.caminho = caminho;
	// }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	// public String getCaminho() {
	// return caminho;
	// }
	//
	// public void setCaminho(String caminho) {
	// this.caminho = caminho;
	// }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toString() {
		return "nome: " + nome + ", descricao: " + descricao + ", URL: " + url;
		// + ", caminho: " + caminho;

	}
}
