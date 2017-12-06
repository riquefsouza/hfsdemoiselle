package br.hfs.util.chart.params;

import java.util.List;

public class ScatterChartParams extends XYParams {
	private static final long serialVersionUID = 1L;
	
	public ScatterChartParams() {
		super();
	}

	public ScatterChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParams> dados) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical, dados);
	}

}
