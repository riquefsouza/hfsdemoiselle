package br.hfs.util.barcode;

import java.io.Serializable;

public class BarCodeParams implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum FormatoImagem {
		SVG, EPS, JPEG, TIFF, PNG, GIF
	}

	public enum TipoBarCode {
		CODABAR, CODE39, POSTNET, INTL2OF5, EAN_128, ROYAL_MAIL_CBC, EAN_13, DATAMATRIX, CODE128, EAN128, PDF417, UPC_A, UPC_E, USPS4CB, EAN_8
	}

	// http://127.0.0.1:8888/genbc?type=intl2of5&msg=123456&height=2.5cm
	// &mw=0.3mm&wf=2&qz=1cm&hrp=top&fmt=jpeg&hrsize=8pt&hrfont=Helvetica&hrpattern=_&res=300&gray=true

	private TipoBarCode tipoBarCode; // type=intl2of5
	private String mensagem; // &msg=123456
	private double altura; // &height=2.5cm
	private double larguraModulo; // &mw=0.3mm
	private int fatorGrandeza; // &wf=2 ou 3

	// Example: 10mw or 1cm. Use "disable" to disable the quiet zone.
	private String zonaCalma; // &qz=1cm

	private BarCodeTexto textoBarCode;
	private FormatoImagem formatoImagem; // &fmt=jpeg
	//ScalableVectorialGraphics
	private boolean SVGEmbutido; // Checked uses <EMBED> (better for Internet
	// Explorer), unchecked uses <OBJECT> (better
	// for Firefox).
	private int resolucao; // &res=300 Applies to bitmap formats only.

	// Applies to bitmap formats only (JPEG, PNG etc.)
	private boolean escalaCinza; // &gray=true

	public BarCodeParams(){
		this.formatoImagem = FormatoImagem.JPEG;
		this.tipoBarCode = TipoBarCode.INTL2OF5;
		this.mensagem = "123456";
		this.altura = 0;
		this.larguraModulo = 0;
		this.fatorGrandeza = 0;
		this.zonaCalma = "disable";
		this.textoBarCode = null;
		this.SVGEmbutido = false;
		this.resolucao = 0;
		this.escalaCinza = false;
	}
	
	public TipoBarCode getTipoBarCode() {
		return tipoBarCode;
	}

	public void setTipoBarCode(TipoBarCode tipoBarCode) {
		this.tipoBarCode = tipoBarCode;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getLarguraModulo() {
		return larguraModulo;
	}

	public void setLarguraModulo(double larguraModulo) {
		this.larguraModulo = larguraModulo;
	}

	public int getFatorGrandeza() {
		return fatorGrandeza;
	}

	public void setFatorGrandeza(int fatorGrandeza) {
		this.fatorGrandeza = fatorGrandeza;
	}

	public String getZonaCalma() {
		return zonaCalma;
	}

	public void setZonaCalma(String zonaCalma) {
		this.zonaCalma = zonaCalma;
	}

	public FormatoImagem getFormatoImagem() {
		return formatoImagem;
	}

	public void setFormatoImagem(FormatoImagem formatoImagem) {
		this.formatoImagem = formatoImagem;
	}

	public int getResolucao() {
		return resolucao;
	}

	public void setResolucao(int resolucao) {
		this.resolucao = resolucao;
	}

	public boolean isEscalaCinza() {
		return escalaCinza;
	}

	public void setEscalaCinza(boolean escalaCinza) {
		this.escalaCinza = escalaCinza;
	}

	public boolean isSVGEmbutido() {
		return SVGEmbutido;
	}

	public void setSVGEmbutido(boolean sVGEmbutido) {
		SVGEmbutido = sVGEmbutido;
	}

	public BarCodeTexto getTextoBarCode() {
		return textoBarCode;
	}

	public void setTextoBarCode(BarCodeTexto textoBarCode) {
		this.textoBarCode = textoBarCode;
	}

	
}
