package br.hfs.util.extenso;

import java.io.Serializable;

public class Extenso implements Serializable {
	private static final long serialVersionUID = 1L;

	private String numero;
	
	private String extenso;
	
	public Extenso() {
		super();
	}

	public Extenso(String numero, String extenso) {
		super();
		this.numero = numero;
		this.extenso = extenso;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getExtenso() {
		return extenso;
	}

	public void setExtenso(String extenso) {
		this.extenso = extenso;
	}

}
