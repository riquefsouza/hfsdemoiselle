package br.hfs.util.pdf;

import java.io.Serializable;

public class PdfInfoFonte implements Serializable {

	private static final long serialVersionUID = 1L;

	private String subTipo;
	private String nome;

	public PdfInfoFonte() {
		super();
	}

	public PdfInfoFonte(String subTipo, String nome) {
		super();
		this.subTipo = subTipo;
		this.nome = nome;
	}

	public String getSubTipo() {
		return subTipo;
	}

	public void setSubTipo(String subTipo) {
		this.subTipo = subTipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "InfoFonte [subTipo=" + subTipo + ", nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((subTipo == null) ? 0 : subTipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PdfInfoFonte other = (PdfInfoFonte) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (subTipo == null) {
			if (other.subTipo != null)
				return false;
		} else if (!subTipo.equals(other.subTipo))
			return false;
		return true;
	}

}
