package br.hfs.util.chart.params;

import java.util.List;

public class XYBarChartParams extends XYParamsDouble {
	private static final long serialVersionUID = 1L;
	
	private boolean mostrarTempoXAxis;

	public XYBarChartParams() {
		super();
	}

	public XYBarChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParamsDouble> series,
			boolean mostrarTempoXAxis) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical, series);
		this.mostrarTempoXAxis = mostrarTempoXAxis;
	}

	public boolean isMostrarTempoXAxis() {
		return mostrarTempoXAxis;
	}

	public void setMostrarTempoXAxis(boolean mostrarTempoXAxis) {
		this.mostrarTempoXAxis = mostrarTempoXAxis;
	}

}
