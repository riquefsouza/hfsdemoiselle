package br.hfs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.hfs.audit.IAuditoria;

@Entity
@Table(name = "entidade")
public class Entidade implements Serializable, IAuditoria {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@NotNull
	@Size(min = 4, max = 64)
	@Column(nullable = false, length = 64)
	private String nome;

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

	@ManyToMany(mappedBy = "entidades")
	private List<Perfil> perfis;

	@Transient
	private List<Permissao> permissoes;

	public Entidade() {
	}

	public Entidade(String nome, List<Perfil> perfis,
			List<Permissao> permissoes, Long idUsuarioInclusao,
			Date dataInclusao, Long idUsuarioAlteracao, Date dataAlteracao) {
		super();
		this.nome = nome;
		this.perfis = perfis;
		this.permissoes = permissoes;
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

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public List<Perfil> getPerfis() {
		return this.perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Entidade other = (Entidade) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}