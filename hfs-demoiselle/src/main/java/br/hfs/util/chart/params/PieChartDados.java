package br.hfs.util.chart.params;

import java.io.Serializable;

public class PieChartDados implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String rotulo;
	private Number dado;

	public PieChartDados(){
		super();
	}
	
	public PieChartDados(String rotulo, Number dado) {
		this.rotulo = rotulo;
		this.dado = dado;
	}

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public Number getDado() {
		return dado;
	}

	public void setDado(Number dado) {
		this.dado = dado;
	}
}
