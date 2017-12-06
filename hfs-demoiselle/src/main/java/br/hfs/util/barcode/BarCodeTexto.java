package br.hfs.util.barcode;

import java.io.Serializable;

public class BarCodeTexto implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum Posicao {
		PADRAO, // é sem o parametro
		TOP, BOTTOM, NENHUMA
	}

	public enum Fonte {
		NENHUMA, HELVETICA,	TIMES_NEW_ROMAN, ARIAL, COURIER_NEW, VERDANA
	}

	private Posicao posicao; // &hrp=top
	private int tamanho; // &hrsize=8pt
	private Fonte fonte; // &hrfont=Helvetica
	// Example: "\_patterned\_:__/__/____" (Any '_' is placeholder for the
	// next message symbol,
	// all other pattern symbols will be inserted between. The '\' is escape
	// char.
	// If the // patterned message is too long you can increase the quite
	// zone lenght to make it visible)
	private String padrao; // &hrpattern=_

	public BarCodeTexto() {
		this.posicao = Posicao.PADRAO;
		this.fonte = Fonte.NENHUMA;
		this.tamanho = 8;
		this.padrao = "";
	}

	public Posicao getPosicao() {
		return posicao;
	}

	public void setPosicao(Posicao posicao) {
		this.posicao = posicao;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public Fonte getFonte() {
		return fonte;
	}

	public void setFonte(Fonte fonte) {
		this.fonte = fonte;
	}

	public String getPadrao() {
		return padrao;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}

}
