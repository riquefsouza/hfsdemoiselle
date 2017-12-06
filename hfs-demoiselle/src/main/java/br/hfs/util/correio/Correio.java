package br.hfs.util.correio;

import java.io.Serializable;

public class Correio implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String uf;
	private String estado;
	private String ufCep1;
	private String ufCep2;
	private String cidade;
	private String logradouro;
	private String bairro;
	private String cep;
	private String tipoLogradouro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUfCep1() {
		return ufCep1;
	}

	public void setUfCep1(String ufCep1) {
		this.ufCep1 = ufCep1;
	}

	public String getUfCep2() {
		return ufCep2;
	}

	public void setUfCep2(String ufCep2) {
		this.ufCep2 = ufCep2;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	@Override
	public String toString() {
		return "Correio [id=" + id + ", uf=" + uf + ", estado=" + estado
				+ ", ufCep1=" + ufCep1 + ", ufCep2=" + ufCep2 + ", cidade="
				+ cidade + ", logradouro=" + logradouro + ", bairro=" + bairro
				+ ", cep=" + cep + ", tipoLogradouro=" + tipoLogradouro + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result
				+ ((tipoLogradouro == null) ? 0 : tipoLogradouro.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		result = prime * result + ((ufCep1 == null) ? 0 : ufCep1.hashCode());
		result = prime * result + ((ufCep2 == null) ? 0 : ufCep2.hashCode());
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
		Correio other = (Correio) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (tipoLogradouro == null) {
			if (other.tipoLogradouro != null)
				return false;
		} else if (!tipoLogradouro.equals(other.tipoLogradouro))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		if (ufCep1 == null) {
			if (other.ufCep1 != null)
				return false;
		} else if (!ufCep1.equals(other.ufCep1))
			return false;
		if (ufCep2 == null) {
			if (other.ufCep2 != null)
				return false;
		} else if (!ufCep2.equals(other.ufCep2))
			return false;
		return true;
	}

}
