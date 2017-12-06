package br.hfs.util.json;

import java.io.Serializable;
import java.util.List;

import br.hfs.util.correio.Correio;

public class JsonParams implements Serializable {
	private static final long serialVersionUID = 1L;

	private int total;
	private double percentual;
	private int ordem;
	private double fracao;
	private int parteFracao;
	private int parte;
	private int resto;
	private int progresso;
	private int percentualInteiro;
	
	private List<Correio> correio;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getPercentual() {
		return percentual;
	}

	public void setPercentual(double percentual) {
		this.percentual = percentual;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public double getFracao() {
		return fracao;
	}

	public void setFracao(double fracao) {
		this.fracao = fracao;
	}

	public int getParte() {
		return parte;
	}

	public void setParte(int parte) {
		this.parte = parte;
	}

	public int getResto() {
		return resto;
	}

	public void setResto(int resto) {
		this.resto = resto;
	}

	public int getProgresso() {
		return progresso;
	}

	public void setProgresso(int progresso) {
		this.progresso = progresso;
	}

	public int getPercentualInteiro() {
		return percentualInteiro;
	}

	public void setPercentualInteiro(int percentualInteiro) {
		this.percentualInteiro = percentualInteiro;
	}

	public int getParteFracao() {
		return parteFracao;
	}

	public void setParteFracao(int parteFracao) {
		this.parteFracao = parteFracao;
	}

	public List<Correio> getCorreio() {
		return correio;
	}

	public void setCorreio(List<Correio> correio) {
		this.correio = correio;
	}

}
