package br.hfs.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="entidade_perfil")
public class EntidadePerfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EntidadePerfilPK id;

	@ManyToMany(mappedBy="entidadesPerfis")
	private List<Permissao> permissoes;

    public EntidadePerfil() {
    }

	public EntidadePerfil(EntidadePerfilPK id) {
		super();
		this.id = id;
	}

	public EntidadePerfilPK getId() {
		return this.id;
	}

	public void setId(EntidadePerfilPK id) {
		this.id = id;
	}
	
	public List<Permissao> getPermissoes() {
		return this.permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	@Override
	public String toString() {
		return id.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		EntidadePerfil other = (EntidadePerfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}