package br.hfs.util.chart.params;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoxAndWhiskerChartDados implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private double media;
	private double mediana;
	private double Q1;
	private double Q3;
	private double minValorRegular;
	private double maxValorRegular;
	private double minAnexo;
	private double maxAnexo;
	private List<?> anexos;

	public BoxAndWhiskerChartDados() {
		this.media = 0;
		this.mediana = 0;
		this.Q1 = 0;
		this.Q3 = 0;
		this.minValorRegular = 0;
		this.maxValorRegular = 0;
		this.minAnexo = 0;
		this.maxAnexo = 0;
		this.anexos = new ArrayList<Integer>();
	}

	public BoxAndWhiskerChartDados(double media, double mediana, double Q1,
			double Q3, double minValorRegular, double maxValorRegular,
			double minAnexo, double maxAnexo, List<?> anexos) {
		this.media = media;
		this.mediana = mediana;
		this.Q1 = Q1;
		this.Q3 = Q3;
		this.minValorRegular = minValorRegular;
		this.maxValorRegular = maxValorRegular;
		this.minAnexo = minAnexo;
		this.maxAnexo = maxAnexo;
		this.anexos = anexos;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public double getMediana() {
		return mediana;
	}

	public void setMediana(double mediana) {
		this.mediana = mediana;
	}

	public double getQ1() {
		return Q1;
	}

	public void setQ1(double q1) {
		Q1 = q1;
	}

	public double getQ3() {
		return Q3;
	}

	public void setQ3(double q3) {
		Q3 = q3;
	}

	public double getMinValorRegular() {
		return minValorRegular;
	}

	public void setMinValorRegular(double minValorRegular) {
		this.minValorRegular = minValorRegular;
	}

	public double getMaxValorRegular() {
		return maxValorRegular;
	}

	public void setMaxValorRegular(double maxValorRegular) {
		this.maxValorRegular = maxValorRegular;
	}

	public double getMinAnexo() {
		return minAnexo;
	}

	public void setMinAnexo(double minAnexo) {
		this.minAnexo = minAnexo;
	}

	public double getMaxAnexo() {
		return maxAnexo;
	}

	public void setMaxAnexo(double maxAnexo) {
		this.maxAnexo = maxAnexo;
	}

	public List<?> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<?> anexos) {
		this.anexos = anexos;
	}
}
