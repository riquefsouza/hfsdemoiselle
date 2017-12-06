package br.hfs.util.chart.params;

import java.util.List;

public class BoxAndWhiskerChartParams extends ChartParams {
	private static final long serialVersionUID = 1L;
	
	private String rotuloCategoriaAxis;
	private String rotuloValorAxis;
	private String chaveLinha;
	private String chaveColuna;
	private List<BoxAndWhiskerChartDados> dados;

	public BoxAndWhiskerChartParams() {
		super();
	}

	public BoxAndWhiskerChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, String rotuloCategoriaAxis,
			String rotuloValorAxis, String chaveLinha, String chaveColuna,
			List<BoxAndWhiskerChartDados> dados) {
		super(largura, altura, titulo, mostrarLegenda, false, false);
		this.rotuloCategoriaAxis = rotuloCategoriaAxis;
		this.rotuloValorAxis = rotuloValorAxis;
		this.chaveLinha = chaveLinha;
		this.chaveColuna = chaveColuna;
		this.dados = dados;
	}

	public String getRotuloCategoriaAxis() {
		return rotuloCategoriaAxis;
	}

	public void setRotuloCategoriaAxis(String rotuloCategoriaAxis) {
		this.rotuloCategoriaAxis = rotuloCategoriaAxis;
	}

	public String getRotuloValorAxis() {
		return rotuloValorAxis;
	}

	public void setRotuloValorAxis(String rotuloValorAxis) {
		this.rotuloValorAxis = rotuloValorAxis;
	}

	public String getChaveLinha() {
		return chaveLinha;
	}

	public void setChaveLinha(String chaveLinha) {
		this.chaveLinha = chaveLinha;
	}

	public String getChaveColuna() {
		return chaveColuna;
	}

	public void setChaveColuna(String chaveColuna) {
		this.chaveColuna = chaveColuna;
	}

	public List<BoxAndWhiskerChartDados> getDados() {
		return dados;
	}

	public void setDados(List<BoxAndWhiskerChartDados> dados) {
		this.dados = dados;
	}

}
