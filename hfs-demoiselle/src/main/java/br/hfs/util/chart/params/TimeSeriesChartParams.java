package br.hfs.util.chart.params;

import java.util.List;

public class TimeSeriesChartParams extends ChartParams {
	private static final long serialVersionUID = 1L;
	
	private String rotuloTempoAxis;
	private String rotuloValorAxis;
	private List<TimeSeriesChartSerie> series;

	public TimeSeriesChartParams() {
		super();
	}

	public TimeSeriesChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloTempoAxis, String rotuloValorAxis,
			List<TimeSeriesChartSerie> series) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs);
		this.rotuloTempoAxis = rotuloTempoAxis;
		this.rotuloValorAxis = rotuloValorAxis;
		this.series = series;
	}

	public String getRotuloTempoAxis() {
		return rotuloTempoAxis;
	}

	public void setRotuloTempoAxis(String rotuloTempoAxis) {
		this.rotuloTempoAxis = rotuloTempoAxis;
	}

	public String getRotuloValorAxis() {
		return rotuloValorAxis;
	}

	public void setRotuloValorAxis(String rotuloValorAxis) {
		this.rotuloValorAxis = rotuloValorAxis;
	}

	public List<TimeSeriesChartSerie> getSeries() {
		return series;
	}

	public void setSeries(List<TimeSeriesChartSerie> series) {
		this.series = series;
	}

}
