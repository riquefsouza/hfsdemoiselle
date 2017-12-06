package br.hfs.util.chart.params;

import java.io.Serializable;

public class SeriesDados implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int dia;
	private int mes;
	private int ano;
	private Number valor;

	public SeriesDados() {
		super();
	}

	public SeriesDados(int dia, int mes, int ano, Number valor) {
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
		this.valor = valor;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Number getValor() {
		return valor;
	}

	public void setValor(Number valor) {
		this.valor = valor;
	}

}