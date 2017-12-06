package br.hfs.util.chart.params;

public class OHLCParams extends ChartParams {
	private static final long serialVersionUID = 1L;
	
	private String rotuloTempoAxis;
	private String rotuloValorAxis;
	private String chave;
	
	public OHLCParams() {
		this.rotuloTempoAxis = "";
		this.rotuloValorAxis = "";
		this.chave = "";
	}

	public OHLCParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloTempoAxis, String rotuloValorAxis, String chave) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs);
		this.rotuloTempoAxis = rotuloTempoAxis;
		this.rotuloValorAxis = rotuloValorAxis;
		this.chave = chave;
	}

	public String getRotuloTempoAxis() {
		return rotuloTempoAxis;
	}

	public void setRotuloTempoAxis(String rotuloTempoAxis) {
		this.rotuloTempoAxis = rotuloTempoAxis;
	}

	public String getRotuloValorAxis() {
		return rotuloValorAxis;
	}

	public void setRotuloValorAxis(String rotuloValorAxis) {
		this.rotuloValorAxis = rotuloValorAxis;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

}
