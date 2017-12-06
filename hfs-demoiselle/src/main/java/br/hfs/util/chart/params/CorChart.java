package br.hfs.util.chart.params;

import java.io.Serializable;

public class CorChart implements Serializable {
	private static final long serialVersionUID = 1L;

	private int red;
	private int green;
	private int blue;

	public CorChart(){
		this.red = 0;
		this.green = 0;
		this.blue = 0;
	}
	
	public CorChart(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
}
