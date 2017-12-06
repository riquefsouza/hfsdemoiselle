package br.hfs.util.chart.params;

import java.io.Serializable;

public class XYSeriesParamsDouble implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeSerie;
	private double[][] dados;

	public XYSeriesParamsDouble() {
		super();
	}

	public XYSeriesParamsDouble(String nomeSerie, double[][] dados) {
		this.nomeSerie = nomeSerie;
		this.dados = dados;
	}

	public String getNomeSerie() {
		return nomeSerie;
	}

	public void setNomeSerie(String nomeSerie) {
		this.nomeSerie = nomeSerie;
	}

	public double[][] getDados() {
		return dados;
	}

	public void setDados(double[][] dados) {
		this.dados = dados;
	}
	
}
