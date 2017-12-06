package br.hfs.view.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.security.CertificadoDigital;
import br.hfs.util.security.CertificadoDigitalVO;
import br.hfs.util.security.SegurancaUtil;

@ManagedBean
public class CertificadoDView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tipo = "arq";
	
	private String arquivo;

	private List<CertificadoDigitalVO> lista;
	
	private DataTable tabelaCertificado;

	@Inject
	private SegurancaUtil segurancaUtil;
	
	@Inject
	private MessageContext messageContext;

	public List<CertificadoDigitalVO> getLista() {
		return lista;
	}

	@PostConstruct
	public void init() {
		lista = new ArrayList<CertificadoDigitalVO>();		
		arquivo = "C:/Demoiselle/workspace/hfs-demoiselle/documentos/certificadoHenrique.cer";
	}
	
	public void carregar(){
		boolean btipo = tipo.equals("arq") ? true : false;
		
		if (btipo)
			carregarArquivo();
		else
			carregarToken();
	}
	
	public void limpar() {
		lista.clear();
		tabelaCertificado.clearInitialState();
	}
		
	private void carregarArquivo(){
		lista.clear();
		
		CertificadoDigital cx = segurancaUtil
				.carregarCertificadoDigitalDeArquivo(arquivo, false);

		lista = cx.toLista();
		Collections.sort(lista);
	}

	private void carregarToken(){
		lista.clear();
		
		try {
			CertificadoDigital cx = segurancaUtil
					.carregarCertificadoDigitalDeToken(false);

			lista = cx.toLista();
			Collections.sort(lista);
		} catch (Exception e) {
			messageContext.add("TOKEN NÃO PLUGADO!");			
		}
	}
	
	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public DataTable getTabelaCertificado() {
		return tabelaCertificado;
	}

	public void setTabelaCertificado(DataTable tabelaCertificado) {
		this.tabelaCertificado = tabelaCertificado;
	}

}
