package br.hfs.util.chart.params;

import java.util.List;

public class XYParams extends XYParamsBase {
	private static final long serialVersionUID = 1L;
	
	private List<XYSeriesParams> series;

	public XYParams() {
		super();
	}

	public XYParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParams> series) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical);
		this.series = series;
	}

	public List<XYSeriesParams> getSeries() {
		return series;
	}

	public void setSeries(List<XYSeriesParams> series) {
		this.series = series;
	}
}
