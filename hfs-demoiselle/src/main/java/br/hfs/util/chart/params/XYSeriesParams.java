package br.hfs.util.chart.params;

import java.io.Serializable;
import java.util.List;

public class XYSeriesParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeSerie;
	private List<XYDados> dados;

	public XYSeriesParams() {
		super();
	}

	public XYSeriesParams(String nomeSerie, List<XYDados> dados) {
		this.nomeSerie = nomeSerie;
		this.dados = dados;
	}

	public String getNomeSerie() {
		return nomeSerie;
	}

	public void setNomeSerie(String nomeSerie) {
		this.nomeSerie = nomeSerie;
	}

	public List<XYDados> getDados() {
		return dados;
	}

	public void setDados(List<XYDados> dados) {
		this.dados = dados;
	}
	
}
