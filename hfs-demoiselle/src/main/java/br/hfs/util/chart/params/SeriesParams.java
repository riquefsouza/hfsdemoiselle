package br.hfs.util.chart.params;

import java.io.Serializable;
import java.util.List;

public class SeriesParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeSerie;
	private List<SeriesDados> dados;

	public SeriesParams(){
		super();
	}
	
	public SeriesParams(String nomeSerie,
			List<SeriesDados> dados) {
		this.nomeSerie = nomeSerie;
		this.dados = dados;
	}

	public String getNomeSerie() {
		return nomeSerie;
	}

	public void setNomeSerie(String nomeSerie) {
		this.nomeSerie = nomeSerie;
	}

	public List<SeriesDados> getDados() {
		return dados;
	}

	public void setDados(List<SeriesDados> dados) {
		this.dados = dados;
	}
}
