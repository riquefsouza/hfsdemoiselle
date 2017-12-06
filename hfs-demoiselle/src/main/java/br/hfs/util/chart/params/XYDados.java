package br.hfs.util.chart.params;

import java.io.Serializable;

public class XYDados implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Number serieX;
	private Number serieY;

	public XYDados(){
		super();
	}
	
	public XYDados(Number serieX, Number serieY) {
		this.serieX = serieX;
		this.serieY = serieY;
	}

	public Number getSerieX() {
		return serieX;
	}

	public void setSerieX(Number serieX) {
		this.serieX = serieX;
	}

	public Number getSerieY() {
		return serieY;
	}

	public void setSerieY(Number serieY) {
		this.serieY = serieY;
	}
}
