package br.hfs.util.textsearch;

import java.io.Serializable;

public class TextSearch implements Serializable {
	private static final long serialVersionUID = 1L;

	private String diretorio;
	private String arquivo;
	
	public TextSearch() {
		super();
	}

	public TextSearch(String diretorio, String arquivo) {
		super();
		this.diretorio = diretorio;
		this.arquivo = arquivo;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	@Override
	public String toString() {
		return "TextSearch [diretorio=" + diretorio + ", arquivo=" + arquivo
				+ "]";
	}

}
