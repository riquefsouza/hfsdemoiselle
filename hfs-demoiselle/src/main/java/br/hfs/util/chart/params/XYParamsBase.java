package br.hfs.util.chart.params;

public class XYParamsBase extends ChartParams {
	private static final long serialVersionUID = 1L;
	
	private String rotuloXAxis;
	private String rotuloYAxis;
	private boolean orientacaoPlotVertical;

	public XYParamsBase() {
		super();
	}

	public XYParamsBase(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis, boolean orientacaoPlotVertical) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs);
		this.rotuloXAxis = rotuloXAxis;
		this.rotuloYAxis = rotuloYAxis;
		this.orientacaoPlotVertical = orientacaoPlotVertical;
	}

	public String getRotuloXAxis() {
		return rotuloXAxis;
	}

	public void setRotuloXAxis(String rotuloXAxis) {
		this.rotuloXAxis = rotuloXAxis;
	}

	public String getRotuloYAxis() {
		return rotuloYAxis;
	}

	public void setRotuloYAxis(String rotuloYAxis) {
		this.rotuloYAxis = rotuloYAxis;
	}

	public boolean isOrientacaoPlotVertical() {
		return orientacaoPlotVertical;
	}

	public void setOrientacaoPlotVertical(boolean orientacaoPlotVertical) {
		this.orientacaoPlotVertical = orientacaoPlotVertical;
	}

}
