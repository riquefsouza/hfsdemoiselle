package br.hfs.util.pdf;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PdfInfo extends PdfPermissao implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numeroPaginas;
	private String titulo;
	private String autor;
	private String assunto;
	private String palavrasChave;
	private String criador;
	private String produtor;
	private Calendar dataCriacao;
	private Calendar dataModificacao;
	private String preso;
	private String metadata;

	public int getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getPalavrasChave() {
		return palavrasChave;
	}

	public void setPalavrasChave(String palavrasChave) {
		this.palavrasChave = palavrasChave;
	}

	public String getCriador() {
		return criador;
	}

	public void setCriador(String criador) {
		this.criador = criador;
	}

	public String getProdutor() {
		return produtor;
	}

	public void setProdutor(String produtor) {
		this.produtor = produtor;
	}

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Calendar getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(Calendar dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public String getPreso() {
		return preso;
	}

	public void setPreso(String preso) {
		this.preso = preso;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		return "PdfInfo [numeroPaginas=" + numeroPaginas + ", titulo=" + titulo
				+ ", autor=" + autor + ", assunto=" + assunto
				+ ", palavrasChave=" + palavrasChave + ", criador=" + criador
				+ ", produtor=" + produtor + ", dataCriacao=" + sm.format(dataCriacao.getTime())
				+ ", dataModificacao=" + sm.format(dataModificacao.getTime()) + ", preso=" + preso
				+ ", metadata=" + metadata + ", podeImprimir=" + isPodeImprimir()
				+ ", podeAgruparDocs=" + isPodeAgruparDocs()
				+ ", podeCopiaConteudo=" + isPodeCopiaConteudo()
				+ ", podeCopiaConteudoAcessibilidade="
				+ isPodeCopiaConteudoAcessibilidade() + ", podeExtracaoPaginas="
				+ isPodeExtracaoPaginas() + ", podeComentarios=" + isPodeComentarios()
				+ ", podePreencherCamposForms=" + isPodePreencherCamposForms()
				+ ", podeAssinatura=" + isPodeAssinatura()
				+ ", podeConverterTodosPontosEmProcesso="
				+ isPodeConverterTodosPontosEmProcesso() + ", somenteLeitura="
				+ isSomenteLeitura() +
				", podeImprimirDegrade=" + isPodeImprimirDegrade() + "]";
	}

	
}
