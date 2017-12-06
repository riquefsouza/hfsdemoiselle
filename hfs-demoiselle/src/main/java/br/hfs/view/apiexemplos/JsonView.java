package br.hfs.view.apiexemplos;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.json.JSONObject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.hfs.util.correio.Correio;
import br.hfs.util.json.IJsonUtilListener;
import br.hfs.util.json.JsonException;
import br.hfs.util.json.JsonUtil;

@ViewController
public class JsonView implements Serializable, IJsonUtilListener {
	private static final long serialVersionUID = 1L;

	private List<Correio> lista;

	private long totalLinhas = 0;
	
	private DecimalFormat fmt = new DecimalFormat("#0.00");
	
	private DataTable tabelaCorreio;
	
	@Inject
	private MessageContext messageContext;

	@Inject
	private JsonUtil jsonUtil;

	@PostConstruct
	public void init(){
		lista = new ArrayList<Correio>();		
		tabelaCorreio = new DataTable();
	}
	
	public void carregar() {
		try {
			String arquivo = "C:/Demoiselle/workspace/hfs-demoiselle/correios/correios-simples.json";
			jsonUtil.lerArquivoJSON(arquivo, this);
		} catch (JsonException e) {
			messageContext.add(e.getMessage());
		}
	}

	@Override
	public void carregando(long total, long ordem, JSONObject jsonObject) {
		Correio co = jsonUtil.fromJSON(ordem, jsonObject);
		lista.add(co);
		
		totalLinhas = ordem;
		
		double percentual = ((double)ordem/(double)total)*100;
		System.out.println(fmt.format(percentual) + "% - "+co.toString());		
	}
	
	public void onComplete() {
		messageContext.add("Carga Completada!");
	}
	
	public void limpar() {
		totalLinhas = 0;
		lista.clear();
		
        //UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:correio");
        //table.setValueExpression("value", null);
		//tabelaCorreio.getChildren().clear();
		tabelaCorreio.clearInitialState();
	}

	public List<Correio> getLista() {
		return lista;
	}

	public long getTotalLinhas() {
		return totalLinhas;
	}

	public void setTotalLinhas(long totalLinhas) {
		this.totalLinhas = totalLinhas;
	}

	public DataTable getTabelaCorreio() {
		return tabelaCorreio;
	}

	public void setTabelaCorreio(DataTable tabelaCorreio) {
		this.tabelaCorreio = tabelaCorreio;
	}

}
