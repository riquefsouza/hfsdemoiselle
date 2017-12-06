package br.hfs.util.feriado;

import java.io.Serializable;
import java.util.Date;

public class Feriado implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private Date data;
	private String dataTexto;

	public Feriado() {
		super();
	}

	public Feriado(String nome, Date data, String dataTexto) {
		super();
		this.nome = nome;
		this.data = data;
		this.dataTexto = dataTexto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDataTexto() {
		return dataTexto;
	}

	public void setDataTexto(String dataTexto) {
		this.dataTexto = dataTexto;
	}

	@Override
	public String toString() {
		return "Feriado [nome=" + nome + ", data=" + data + ", dataTexto="
				+ dataTexto + "]";
	}

	
}
