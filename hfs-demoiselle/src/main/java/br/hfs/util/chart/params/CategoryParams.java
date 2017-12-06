package br.hfs.util.chart.params;

import java.util.ArrayList;
import java.util.List;

public class CategoryParams extends ChartParams {
	private static final long serialVersionUID = 1L;
	
	private String rotuloCategoriaAxis;
	private String rotuloValorAxis;
	private boolean orientacaoPlotVertical;
	private List<CategoryDados> dados;

	public CategoryParams() {
		super();
		this.rotuloCategoriaAxis = "";
		this.rotuloValorAxis = "";
		this.orientacaoPlotVertical = false;
		this.dados = new ArrayList<CategoryDados>();
	}

	public CategoryParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloCategoriaAxis, String rotuloValorAxis,
			boolean orientacaoPlotVertical, List<CategoryDados> dados) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs);
		this.rotuloCategoriaAxis = rotuloCategoriaAxis;
		this.rotuloValorAxis = rotuloValorAxis;
		this.orientacaoPlotVertical = orientacaoPlotVertical;
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

	public boolean isOrientacaoPlotVertical() {
		return orientacaoPlotVertical;
	}

	public void setOrientacaoPlotVertical(boolean orientacaoPlotVertical) {
		this.orientacaoPlotVertical = orientacaoPlotVertical;
	}

	public List<CategoryDados> getDados() {
		return dados;
	}

	public void setDados(List<CategoryDados> dados) {
		this.dados = dados;
	}

}
