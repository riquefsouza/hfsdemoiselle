package br.hfs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EntidadePerfilPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "entidade_id")
	private int entidadeId;

	@Column(name = "perfil_id")
	private int perfilId;

	public EntidadePerfilPK() {
	}

	public EntidadePerfilPK(int entidadeId, int perfilId) {
		super();
		this.entidadeId = entidadeId;
		this.perfilId = perfilId;
	}

	public int getEntidadeId() {
		return this.entidadeId;
	}

	public void setEntidadeId(int entidadeId) {
		this.entidadeId = entidadeId;
	}

	public int getPerfilId() {
		return this.perfilId;
	}

	public void setPerfilId(int perfilId) {
		this.perfilId = perfilId;
	}
	
	@Override
	public String toString(){
		return "perfilId: " + perfilId + " - entidadeId: " + entidadeId; 
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EntidadePerfilPK)) {
			return false;
		}
		EntidadePerfilPK castOther = (EntidadePerfilPK) other;
		return (this.entidadeId == castOther.entidadeId)
				&& (this.perfilId == castOther.perfilId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.entidadeId;
		hash = hash * prime + this.perfilId;

		return hash;
	}
}