package br.hfs.util.criptografia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProvedorSeguranca implements Serializable {
	private static final long serialVersionUID = 1L;

	private String provedor;
	private String nome;
	private String info;
	private double versao;
	private List<String> servicos;

	public ProvedorSeguranca() {
		super();
		this.provedor = "";
		this.nome = "";
		this.info = "";
		this.versao = 0;
		this.servicos = new ArrayList<String>();
	}

	public String getProvedor() {
		return provedor;
	}

	public void setProvedor(String provedor) {
		this.provedor = provedor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public double getVersao() {
		return versao;
	}

	public void setVersao(double versao) {
		this.versao = versao;
	}

	public List<String> getServicos() {
		return servicos;
	}

	public void setServicos(List<String> servicos) {
		this.servicos = servicos;
	}

	@Override
	public String toString() {
		return "ProvedorSeguranca [provedor=" + provedor + ", nome=" + nome
				+ ", info=" + info + ", versao=" + versao + ", servicos="
				+ servicos + "]";
	}

}
