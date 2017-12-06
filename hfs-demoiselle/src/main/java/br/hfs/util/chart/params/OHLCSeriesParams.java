package br.hfs.util.chart.params;

import java.io.Serializable;
import java.util.List;

public class OHLCSeriesParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeSerie;
	private List<OHLCSeriesDados> dados;

	public OHLCSeriesParams() {
		super();
	}

	public OHLCSeriesParams(String nomeSerie, List<OHLCSeriesDados> dados) {
		this.nomeSerie = nomeSerie;
		this.dados = dados;
	}

	public String getNomeSerie() {
		return nomeSerie;
	}

	public void setNomeSerie(String nomeSerie) {
		this.nomeSerie = nomeSerie;
	}

	public List<OHLCSeriesDados> getDados() {
		return dados;
	}

	public void setDados(List<OHLCSeriesDados> dados) {
		this.dados = dados;
	}
}
