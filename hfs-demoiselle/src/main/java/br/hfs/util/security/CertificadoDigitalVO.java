package br.hfs.util.security;

import java.io.Serializable;

public class CertificadoDigitalVO implements Serializable,
		Comparable<CertificadoDigitalVO>, Cloneable {

	private static final long serialVersionUID = 1L;

	private String item;

	private String descricao;

	public CertificadoDigitalVO() {
		this.item = "";
		this.descricao = "";
	}

	public CertificadoDigitalVO(String item, String descricao) {
		this.item = item;
		this.descricao = descricao;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String toString() {
		return item + " = " + descricao;
	}

	public Object clone() {
		if (item != null && descricao != null) {
			return new CertificadoDigitalVO(item, descricao);
		}
		return new CertificadoDigitalVO();
	}

	public int compareTo(CertificadoDigitalVO outro) {
		return item.compareTo(outro.item);
	}

}
