package br.hfs.util.ldap;

import java.io.Serializable;

public class LdapAtributo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String valor;

	public LdapAtributo() {
		super();
	}

	public LdapAtributo(String id, String valor) {
		super();
		this.id = id;
		this.valor = valor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
