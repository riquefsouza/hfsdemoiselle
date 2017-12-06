package br.hfs.view.apiexemplos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.hfs.util.textsearch.TextSearch;
import br.hfs.util.textsearch.TextSearchException;
import br.hfs.util.textsearch.TextSearchUtil;

@ViewController
public class TextSearchView implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<TextSearch> lista;

	private long totalLinhas = 0;
	
	private String diretorio;
	
	private String pesquisa;

	private boolean desabilitaBtnCarregar;
	
	private boolean desabilitaBtnLimpar;
	
	@Inject
	private MessageContext messageContext;

	@Inject
	private TextSearchUtil tsUtil;

	@PostConstruct
	public void init() {
		lista = new ArrayList<TextSearch>();
		//tabelaArquivos = new DataTable();
		diretorio = "c:/lucene-index";
		pesquisa = "string";
		desabilitaBtnCarregar = false;
		desabilitaBtnLimpar = true;
	}

	public void carregar() {
		try {
			lista = tsUtil
					.pesquisarArquivos(diretorio, pesquisa, 10000);
			totalLinhas = lista.size();
			
			desabilitaBtnCarregar = true;
			desabilitaBtnLimpar = false;			
		} catch (TextSearchException e) {
			messageContext.add(e.getMessage());
		}
	}

	public void limpar() {
		totalLinhas = 0;
		lista.clear();
		desabilitaBtnCarregar = false;
		desabilitaBtnLimpar = true;		
	}

	public List<TextSearch> getLista() {
		return lista;
	}

	public long getTotalLinhas() {
		return totalLinhas;
	}

	public void setTotalLinhas(long totalLinhas) {
		this.totalLinhas = totalLinhas;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public boolean isDesabilitaBtnCarregar() {
		return desabilitaBtnCarregar;
	}

	public void setDesabilitaBtnCarregar(boolean desabilitaBtnCarregar) {
		this.desabilitaBtnCarregar = desabilitaBtnCarregar;
	}

	public boolean isDesabilitaBtnLimpar() {
		return desabilitaBtnLimpar;
	}

	public void setDesabilitaBtnLimpar(boolean desabilitaBtnLimpar) {
		this.desabilitaBtnLimpar = desabilitaBtnLimpar;
	}


}
