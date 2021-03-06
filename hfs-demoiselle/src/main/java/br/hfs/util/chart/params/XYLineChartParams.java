package br.hfs.util.chart.params;

import java.util.List;

public class XYLineChartParams extends XYParams {
	private static final long serialVersionUID = 1L;
	
	public XYLineChartParams() {
		super();
	}

	public XYLineChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParams> dados) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical, dados);
	}

}
