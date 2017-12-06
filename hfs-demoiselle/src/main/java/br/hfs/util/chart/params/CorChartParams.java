package br.hfs.util.chart.params;

import java.io.Serializable;

public class CorChartParams implements Serializable {
	private static final long serialVersionUID = 1L;

	private CorChart corFundoChart;
	private CorChart corTituloChart;
	private CorChart corFundoPlot;
	private CorChart corLinhasGridPlot;

	public CorChartParams() {
		this.corFundoChart = new CorChart();
		this.corTituloChart = new CorChart();
		this.corFundoPlot = new CorChart();
		this.corLinhasGridPlot = new CorChart();
	}

	public CorChartParams(CorChart corFundoChart, CorChart corTituloChart,
			CorChart corFundoPlot, CorChart corLinhasGridPlot) {
		this.corFundoChart = corFundoChart;
		this.corTituloChart = corTituloChart;
		this.corFundoPlot = corFundoPlot;
		this.corLinhasGridPlot = corLinhasGridPlot;
	}

	public CorChart getCorFundoChart() {
		return corFundoChart;
	}

	public void setCorFundoChart(CorChart corFundoChart) {
		this.corFundoChart = corFundoChart;
	}

	public CorChart getCorTituloChart() {
		return corTituloChart;
	}

	public void setCorTituloChart(CorChart corTituloChart) {
		this.corTituloChart = corTituloChart;
	}

	public CorChart getCorFundoPlot() {
		return corFundoPlot;
	}

	public void setCorFundoPlot(CorChart corFundoPlot) {
		this.corFundoPlot = corFundoPlot;
	}

	public CorChart getCorLinhasGridPlot() {
		return corLinhasGridPlot;
	}

	public void setCorLinhasGridPlot(CorChart corLinhasGridPlot) {
		this.corLinhasGridPlot = corLinhasGridPlot;
	}

}
