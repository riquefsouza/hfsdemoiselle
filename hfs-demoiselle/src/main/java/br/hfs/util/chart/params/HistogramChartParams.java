package br.hfs.util.chart.params;

import java.util.List;

public class HistogramChartParams extends XYParamsDouble {
	private static final long serialVersionUID = 1L;
	
	public HistogramChartParams() {
		super();
	}

	public HistogramChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParamsDouble> series) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical, series);
	}

}
