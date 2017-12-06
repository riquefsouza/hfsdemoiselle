package br.hfs.util.chart.params;

import java.io.Serializable;

public class GanttChartDados implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nomeSerie;
	private Number[] partidas;
	private Number[] chegadas;

	public GanttChartDados() {
		this.nomeSerie = "";
		this.partidas = new Number[0];
		this.chegadas = new Number[0];
	}

	public GanttChartDados(String nomeSerie, Number[] partidas,
			Number[] chegadas) {
		this.nomeSerie = nomeSerie;
		this.partidas = partidas;
		this.chegadas = chegadas;
	}

	public String getNomeSerie() {
		return nomeSerie;
	}

	public void setNomeSerie(String nomeSerie) {
		this.nomeSerie = nomeSerie;
	}

	public Number[] getPartidas() {
		return partidas;
	}

	public void setPartidas(Number[] partidas) {
		this.partidas = partidas;
	}

	public Number[] getChegadas() {
		return chegadas;
	}

	public void setChegadas(Number[] chegadas) {
		this.chegadas = chegadas;
	}

}
