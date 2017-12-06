package br.hfs.util.rss;

import java.io.Serializable;
import java.util.Date;

public class RssEntrada implements Serializable {
	private static final long serialVersionUID = 1L;

	private String titulo;
	private String link;
	private String autor;
	private Date dataPublicacao;
	private String descricao;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "RssEntrada [titulo=" + titulo + ", link=" + link + ", autor="
				+ autor + ", dataPublicacao=" + dataPublicacao + ", descricao="
				+ descricao + "]";
	}

}
