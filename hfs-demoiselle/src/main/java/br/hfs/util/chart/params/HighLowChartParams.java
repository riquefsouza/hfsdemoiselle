package br.hfs.util.chart.params;

import java.util.List;

public class HighLowChartParams extends OHLCParams {
	private static final long serialVersionUID = 1L;
	
	private List<OHLCSeriesParams> series;

	public HighLowChartParams() {
		super();
	}

	public HighLowChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloTempoAxis, String rotuloValorAxis, String chave,
			List<OHLCSeriesParams> series) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloTempoAxis, rotuloValorAxis, chave);
		this.series = series;
	}

	public List<OHLCSeriesParams> getSeries() {
		return series;
	}

	public void setSeries(List<OHLCSeriesParams> series) {
		this.series = series;
	}

}
