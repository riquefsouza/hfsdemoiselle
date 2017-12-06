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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.gov.frameworkdemoiselle.validation.annotation.Cpf;
import br.hfs.audit.IAuditoria;
import br.hfs.util.xml.DateXMLConverter;

import com.thoughtworks.xstream.annotations.XStreamConverter;

@NamedQueries({
		@NamedQuery(name = "Usuario.logar", query = "SELECT 1 FROM Usuario u WHERE u.login = :login and u.senha = :senha"),
		@NamedQuery(name = "Usuario.autenticado", query = "SELECT u FROM Usuario u WHERE u.login = :login and u.senha = :senha"),
		@NamedQuery(name = "Usuario.total", query = "select count(u) from Usuario u") })
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable, IAuditoria, ILoggable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@Size(min = 5, max = 64)
	@Column(nullable = false, length = 64)
	private String login;

	@NotNull
	@Size(min = 5, max = 128)
	@Column(nullable = false, length = 128)
	private String senha;

	@NotNull
	@Size(min = 5, max = 150)
	@Column(nullable = false, length = 150)
	private String nome;

	@NotNull
	@Size(min = 5, max = 200)
	// @Email
	// @Pattern(regexp="\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(.\\w{2,4})+")
	// @Pattern(regexp="[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]")
	@Column(nullable = false, length = 200)
	private String email;

	@Cpf
	@Column(nullable = true)
	private String cpf;

	@Size(max = 255)
	@Column(nullable = true)
	private String arquivoFoto;

	@Lob
	@Column(nullable = true)
	private byte[] foto;

	@Column(nullable = false)
	private Long idUsuarioInclusao;

	@XStreamConverter(DateXMLConverter.class)
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInclusao;

	@Column(nullable = true)
	private Long idUsuarioAlteracao;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date dataAlteracao;

	@ManyToMany
	@JoinTable(name = "perfil_usuario", 
	joinColumns = { @JoinColumn(name = "usuario_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "perfil_id") })
	private List<Perfil> perfis;

	public Usuario() {
		super();
	}

	public Usuario(String login, String senha, String nome, String email,
			String cpf, List<Perfil> perfis, Long idUsuarioInclusao, Date dataInclusao,
			Long idUsuarioAlteracao, Date dataAlteracao) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.perfis = perfis;
		this.idUsuarioInclusao = idUsuarioInclusao;
		this.dataInclusao = dataInclusao;
		this.idUsuarioAlteracao = idUsuarioAlteracao;
		this.dataAlteracao = dataAlteracao;
	}

	public Usuario(Long id, String login, String senha, String nome,
			String email, String cpf, List<Perfil> perfis, Long idUsuarioInclusao,
			Date dataInclusao, Long idUsuarioAlteracao, Date dataAlteracao) {
		this(login, senha, nome, email, cpf, perfis, idUsuarioInclusao, dataInclusao,
				idUsuarioAlteracao, dataAlteracao);
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getArquivoFoto() {
		return arquivoFoto;
	}

	public void setArquivoFoto(String arquivoFoto) {
		this.arquivoFoto = arquivoFoto;
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

	@Override
	public String getInfo() {
		String info = this.getClass().toString()
				.substring(this.getClass().toString().lastIndexOf(".") + 1);
		info += " ";
		info += (this.getId() == 0) ? "um novo registro"
				: " o registro numero " + this.getId();
		return info;
	}

	@Override
	public String toString() {
		return nome;
	}

}