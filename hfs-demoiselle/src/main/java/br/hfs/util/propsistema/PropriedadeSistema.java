package br.hfs.util.propsistema;

import java.io.Serializable;

public class PropriedadeSistema implements Serializable,
		Comparable<PropriedadeSistema>, Cloneable {

	private static final long serialVersionUID = 1L;

	private String nome;

	private String valor;

	public PropriedadeSistema() {
		this.nome = "";
		this.valor = "";
	}

	public PropriedadeSistema(String nome, String valor) {
		this.nome = nome;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String toString() {
		return nome + "=" + valor;
	}

	public Object clone() {
		if (nome != null && valor != null) {
			return new PropriedadeSistema(nome, valor);
		}
		return new PropriedadeSistema();
	}

	public int compareTo(PropriedadeSistema outro) {
		return nome.compareTo(outro.nome);
	}
}
