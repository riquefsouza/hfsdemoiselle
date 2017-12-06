package br.hfs.util.chart.params;

public class OHLCSeriesDados extends SeriesDados {
	private static final long serialVersionUID = 1L;
	
	private double valor2;
	private double valor3;
	private double valor4;

	public OHLCSeriesDados() {
		super();
		this.valor2 = 0;
		this.valor3 = 0;
		this.valor4 = 0;
	}

	public OHLCSeriesDados(int dia, int mes, int ano, double valor,
			double valor2, double valor3, double valor4) {
		super(dia, mes, ano, valor);
		this.valor2 = valor2;
		this.valor3 = valor3;
		this.valor4 = valor4;
	}

	public double getValor2() {
		return valor2;
	}

	public void setValor2(double valor2) {
		this.valor2 = valor2;
	}

	public double getValor3() {
		return valor3;
	}

	public void setValor3(double valor3) {
		this.valor3 = valor3;
	}

	public double getValor4() {
		return valor4;
	}

	public void setValor4(double valor4) {
		this.valor4 = valor4;
	}

}
