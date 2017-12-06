package br.hfs.util.chart.params;

import java.io.Serializable;

public class ChartParams implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int largura;
	private int altura;
	private String titulo;
	private boolean mostrarLegenda;
	private boolean usarTooltips;
	private boolean gerarURLs;
	private CorChartParams corParams;

	public ChartParams() {
		this.largura = 0;
		this.altura = 0;
		this.titulo = "";
		this.mostrarLegenda = false;
		this.usarTooltips = false;
		this.gerarURLs = false;
		this.corParams = null;
	}

	public ChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs) {
		this.largura = largura;
		this.altura = altura;
		this.titulo = titulo;
		this.mostrarLegenda = mostrarLegenda;
		this.usarTooltips = usarTooltips;
		this.gerarURLs = gerarURLs;
		this.corParams = null;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isMostrarLegenda() {
		return mostrarLegenda;
	}

	public void setMostrarLegenda(boolean mostrarLegenda) {
		this.mostrarLegenda = mostrarLegenda;
	}

	public boolean isUsarTooltips() {
		return usarTooltips;
	}

	public void setUsarTooltips(boolean usarTooltips) {
		this.usarTooltips = usarTooltips;
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public boolean isGerarURLs() {
		return gerarURLs;
	}

	public void setGerarURLs(boolean gerarURLs) {
		this.gerarURLs = gerarURLs;
	}

	public CorChartParams getCorParams() {
		return corParams;
	}

	public void setCorParams(CorChartParams corParams) {
		this.corParams = corParams;
	}
}
