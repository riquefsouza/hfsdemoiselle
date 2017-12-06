package br.hfs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.hfs.audit.IAuditoria;

@Entity
@Table(name = "permissao")
public class Permissao implements Serializable, IAuditoria {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@NotNull
	@Size(min = 5, max = 64)
	@Column(nullable = false, length = 64)
	private String permissao;

	@Column(nullable = false)
	private Long idUsuarioInclusao;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;

	@Column(nullable = true)
	private Long idUsuarioAlteracao;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date dataAlteracao;

	@ManyToMany
	@JoinTable(name = "permissao_entidade_perfil", joinColumns = { @JoinColumn(name = "permissao_id") }, inverseJoinColumns = {
			@JoinColumn(name = "pep_entidade_id", referencedColumnName = "entidade_id"),
			@JoinColumn(name = "pep_perfil_id", referencedColumnName = "perfil_id") })
	private List<EntidadePerfil> entidadesPerfis;

	public Permissao() {
	}

	public Permissao(String permissao, List<EntidadePerfil> entidadesPerfis,
			Long idUsuarioInclusao, Date dataInclusao, Long idUsuarioAlteracao,
			Date dataAlteracao) {
		super();
		this.permissao = permissao;
		this.entidadesPerfis = entidadesPerfis;
		this.idUsuarioInclusao = idUsuarioInclusao;
		this.dataInclusao = dataInclusao;
		this.idUsuarioAlteracao = idUsuarioAlteracao;
		this.dataAlteracao = dataAlteracao;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPermissao() {
		return this.permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	@Override
	public Long getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}

	@Override
	public void setIdUsuarioInclusao(Long idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}

	@Override
	public Date getDataInclusao() {
		return dataInclusao;
	}

	@Override
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	@Override
	public Long getIdUsuarioAlteracao() {
		return idUsuarioAlteracao;
	}

	@Override
	public void setIdUsuarioAlteracao(Long idUsuarioAlteracao) {
		this.idUsuarioAlteracao = idUsuarioAlteracao;
	}

	@Override
	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	@Override
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public List<EntidadePerfil> getEntidadesPerfis() {
		return this.entidadesPerfis;
	}

	public void setEntidadesHasPerfis(List<EntidadePerfil> entidadesPerfis) {
		this.entidadesPerfis = entidadesPerfis;
	}
	
	@Override
	public String toString() {
		return permissao;
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
		Permissao other = (Permissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}