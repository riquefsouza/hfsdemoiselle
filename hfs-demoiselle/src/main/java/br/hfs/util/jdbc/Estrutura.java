package br.hfs.util.jdbc;

import java.io.Serializable;
import java.util.Date;

public class Estrutura implements Serializable {

	private static final long serialVersionUID = 1L;

	private EstruturaPK id;

	private Date dataInicial;

	private Date dataFinal;

	private int ativada;

	private String descricao;

	private String sigla;

	private String categoria;

	private Integer codigoPai;

	public Estrutura() {
		super();
	}

	public EstruturaPK getId() {
		return id;
	}

	public void setId(EstruturaPK id) {
		this.id = id;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public int getAtivada() {
		return ativada;
	}

	public void setAtivada(int ativada) {
		this.ativada = ativada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Integer getCodigoPai() {
		return codigoPai;
	}

	public void setCodigoPai(Integer codigoPai) {
		this.codigoPai = codigoPai;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
