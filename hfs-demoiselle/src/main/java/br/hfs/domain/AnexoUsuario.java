package br.hfs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQuery(name = "AnexoUsuario.listarPorId", query = "select au from AnexoUsuario au where au.idUsuario = :idUsuario")
@Entity
@Table(name = "anexo_usuario")
public class AnexoUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "nome_arquivo")
	private String nomeArquivo;

	@Lob
	@Column(name = "anexo")
	private byte[] anexo;

	@Column(name = "id_usuario")
	private Long idUsuario;	
	
	public AnexoUsuario() {
		super();
	}
	
	public AnexoUsuario(String nomeArquivo, Long idUsuario) {
		super();
		this.nomeArquivo = nomeArquivo;
		this.idUsuario = idUsuario;
	}

	public AnexoUsuario(String nomeArquivo, Long idUsuario, byte[] anexo) {
		super();
		this.nomeArquivo = nomeArquivo;
		this.idUsuario = idUsuario;
		this.anexo = anexo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public byte[] getAnexo() {
		return anexo;
	}

	public void setAnexo(byte[] anexo) {
		this.anexo = anexo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

}
