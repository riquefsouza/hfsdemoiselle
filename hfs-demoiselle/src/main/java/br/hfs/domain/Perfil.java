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
@Table(name = "perfil")
public class Perfil implements Serializable, IAuditoria {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@NotNull
	@Size(min = 5, max = 64)
	@Column(nullable = false, length = 64)
	private String perfil;

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
	@JoinTable(name = "entidade_perfil", 
	joinColumns = { @JoinColumn(name = "perfil_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "entidade_id") })
	private List<Entidade> entidades;

	@ManyToMany(mappedBy = "perfis")
	private List<Usuario> usuarios;

	public Perfil() {
	}

	public Perfil(String perfil, List<Entidade> entidades,
			List<Usuario> usuarios, Long idUsuarioInclusao, Date dataInclusao,
			Long idUsuarioAlteracao, Date dataAlteracao) {
		super();
		this.perfil = perfil;
		this.entidades = entidades;
		this.usuarios = usuarios;
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

	public String getPerfil() {
		return this.perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
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

	public List<Entidade> getEntidades() {
		return this.entidades;
	}

	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		return perfil;
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
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}