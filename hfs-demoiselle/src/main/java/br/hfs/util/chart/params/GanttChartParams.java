package br.hfs.util.chart.params;

public class GanttChartParams extends ChartParams {
	private static final long serialVersionUID = 1L;

	private String rotuloCategoriaAxis;
	private String rotuloDataAxis;
	private GanttChartDados[] dados;

	public GanttChartParams() {
		this.rotuloCategoriaAxis = "";
		this.rotuloDataAxis = "";
		this.dados = new GanttChartDados[0]; 
	}

	public GanttChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloCategoriaAxis, String rotuloDataAxis, GanttChartDados[] dados) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs);
		this.rotuloCategoriaAxis = rotuloCategoriaAxis;
		this.rotuloDataAxis = rotuloDataAxis;
		this.dados = dados;
	}

	public String getRotuloCategoriaAxis() {
		return rotuloCategoriaAxis;
	}

	public void setRotuloCategoriaAxis(String rotuloCategoriaAxis) {
		this.rotuloCategoriaAxis = rotuloCategoriaAxis;
	}

	public String getRotuloDataAxis() {
		return rotuloDataAxis;
	}

	public void setRotuloDataAxis(String rotuloDataAxis) {
		this.rotuloDataAxis = rotuloDataAxis;
	}

	public GanttChartDados[] getDados() {
		return dados;
	}

	public void setDados(GanttChartDados[] dados) {
		this.dados = dados;
	}

}
