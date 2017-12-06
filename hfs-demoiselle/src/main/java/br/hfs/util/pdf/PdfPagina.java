package br.hfs.util.pdf;

import java.io.Serializable;
import java.util.List;

public class PdfPagina implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numero;

	private List<String> texto;

	public PdfPagina(int numero, List<String> texto) {
		super();
		this.numero = numero;
		this.texto = texto;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<String> getTexto() {
		return texto;
	}

	public void setTexto(List<String> texto) {
		this.texto = texto;
	}

	@Override
	public String toString() {
		return "PdfPagina [numero=" + numero + ", texto=" + texto + "]";
	}

}
