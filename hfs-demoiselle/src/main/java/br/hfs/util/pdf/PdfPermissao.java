package br.hfs.util.pdf;

import java.io.Serializable;

public class PdfPermissao implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean podeImprimir;
	private boolean podeAgruparDocs;
	private boolean podeCopiaConteudo;
	private boolean podeCopiaConteudoAcessibilidade;
	private boolean podeExtracaoPaginas;
	private boolean podeComentarios;
	private boolean podePreencherCamposForms;
	private boolean podeAssinatura;
	private boolean podeConverterTodosPontosEmProcesso;
	private boolean somenteLeitura;
	private boolean podeImprimirDegrade;
	
	private String senhaProprietario;
	private String senhaUsuario;
		
	public PdfPermissao() {
		super();
		this.senhaProprietario = "";
		this.senhaUsuario = "";
	}

	public PdfPermissao(boolean podeImprimir, boolean podeAgruparDocs, boolean podeCopiaConteudo,
			boolean podeCopiaConteudoAcessibilidade, boolean podeExtracaoPaginas, boolean podeComentarios,
			boolean podePreencherCamposForms, boolean podeAssinatura, boolean podeConverterTodosPontosEmProcesso,
			boolean somenteLeitura, boolean podeImprimirDegrade) {
		super();
		this.podeImprimir = podeImprimir;
		this.podeAgruparDocs = podeAgruparDocs;
		this.podeCopiaConteudo = podeCopiaConteudo;
		this.podeCopiaConteudoAcessibilidade = podeCopiaConteudoAcessibilidade;
		this.podeExtracaoPaginas = podeExtracaoPaginas;
		this.podeComentarios = podeComentarios;
		this.podePreencherCamposForms = podePreencherCamposForms;
		this.podeAssinatura = podeAssinatura;
		this.podeConverterTodosPontosEmProcesso = podeConverterTodosPontosEmProcesso;
		this.somenteLeitura = somenteLeitura;
		this.podeImprimirDegrade = podeImprimirDegrade;		
	}

	public boolean isPodeImprimir() {
		return podeImprimir;
	}

	public void setPodeImprimir(boolean podeImprimir) {
		this.podeImprimir = podeImprimir;
	}

	public boolean isPodeAgruparDocs() {
		return podeAgruparDocs;
	}

	public void setPodeAgruparDocs(boolean podeAgruparDocs) {
		this.podeAgruparDocs = podeAgruparDocs;
	}

	public boolean isPodeCopiaConteudo() {
		return podeCopiaConteudo;
	}

	public void setPodeCopiaConteudo(boolean podeCopiaConteudo) {
		this.podeCopiaConteudo = podeCopiaConteudo;
	}

	public boolean isPodeCopiaConteudoAcessibilidade() {
		return podeCopiaConteudoAcessibilidade;
	}

	public void setPodeCopiaConteudoAcessibilidade(
			boolean podeCopiaConteudoAcessibilidade) {
		this.podeCopiaConteudoAcessibilidade = podeCopiaConteudoAcessibilidade;
	}

	public boolean isPodeExtracaoPaginas() {
		return podeExtracaoPaginas;
	}

	public void setPodeExtracaoPaginas(boolean podeExtracaoPaginas) {
		this.podeExtracaoPaginas = podeExtracaoPaginas;
	}

	public boolean isPodeComentarios() {
		return podeComentarios;
	}

	public void setPodeComentarios(boolean podeComentarios) {
		this.podeComentarios = podeComentarios;
	}

	public boolean isPodePreencherCamposForms() {
		return podePreencherCamposForms;
	}

	public void setPodePreencherCamposForms(boolean podePreencherCamposForms) {
		this.podePreencherCamposForms = podePreencherCamposForms;
	}

	public boolean isPodeAssinatura() {
		return podeAssinatura;
	}

	public void setPodeAssinatura(boolean podeAssinatura) {
		this.podeAssinatura = podeAssinatura;
	}

	public boolean isPodeConverterTodosPontosEmProcesso() {
		return podeConverterTodosPontosEmProcesso;
	}

	public void setPodeConverterTodosPontosEmProcesso(
			boolean podeConverterTodosPontosEmProcesso) {
		this.podeConverterTodosPontosEmProcesso = podeConverterTodosPontosEmProcesso;
	}

	public boolean isSomenteLeitura() {
		return somenteLeitura;
	}

	public void setSomenteLeitura(boolean somenteLeitura) {
		this.somenteLeitura = somenteLeitura;
	}

	public boolean isPodeImprimirDegrade() {
		return podeImprimirDegrade;
	}

	public void setPodeImprimirDegrade(boolean podeImprimirDegrade) {
		this.podeImprimirDegrade = podeImprimirDegrade;
	}

	public String getSenhaProprietario() {
		return senhaProprietario;
	}

	public void setSenhaProprietario(String senhaProprietario) {
		this.senhaProprietario = senhaProprietario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}
	
	public void setTodasPermissoes(boolean pode) {
		this.podeImprimir = pode;
		this.podeAgruparDocs = pode;
		this.podeCopiaConteudo = pode;
		this.podeCopiaConteudoAcessibilidade = pode;
		this.podeExtracaoPaginas = pode;
		this.podeComentarios = pode;
		this.podePreencherCamposForms = pode;
		this.podeAssinatura = pode;
		this.podeConverterTodosPontosEmProcesso = pode;
		this.somenteLeitura = !pode;
		this.podeImprimirDegrade = pode;
	}
	
	@Override
	public String toString() {
		return "PdfPermissao [podeImprimir=" + podeImprimir + ", podeAgruparDocs=" + podeAgruparDocs
				+ ", podeCopiaConteudo=" + podeCopiaConteudo + ", podeCopiaConteudoAcessibilidade="
				+ podeCopiaConteudoAcessibilidade + ", podeExtracaoPaginas=" + podeExtracaoPaginas
				+ ", podeComentarios=" + podeComentarios + ", podePreencherCamposForms=" + podePreencherCamposForms
				+ ", podeAssinatura=" + podeAssinatura + ", podeConverterTodosPontosEmProcesso="
				+ podeConverterTodosPontosEmProcesso + ", somenteLeitura=" + somenteLeitura + 
				", podeImprimirDegrade=" + podeImprimirDegrade + "]";
	}
	
}
