package br.hfs.util.chart.params;

import java.util.List;

public class PieChartParams extends ChartParams {
	private static final long serialVersionUID = 1L;
	
	private boolean usarPie3D;
	private List<PieChartDados> dados;

	public PieChartParams() {
		super();
	}

	public PieChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			boolean usarPie3D, List<PieChartDados> dados) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs);
		this.usarPie3D = usarPie3D;
		this.dados = dados;
	}

	public List<PieChartDados> getDados() {
		return dados;
	}

	public void setDados(List<PieChartDados> dados) {
		this.dados = dados;
	}

	public boolean isUsarPie3D() {
		return usarPie3D;
	}

	public void setUsarPie3D(boolean usarPie3D) {
		this.usarPie3D = usarPie3D;
	}
}
