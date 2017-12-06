package br.hfs.view.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Hex;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.criptografia.Criptografia;
import br.hfs.util.criptografia.CriptografiaException;
import br.hfs.util.criptografia.CriptografiaUtil;
import br.hfs.util.criptografia.CriptografiaUtil.GeradorChave;

@ManagedBean
@RequestScoped
public class CriptografiaView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String algoritmo;
	private List<SelectItem> listaAlgoritmo;
	private String chaveUnica;
	private String chaveUnicaFormato;
	private String textoPuro;
	private String textoCriptografado;
	private String textoDescriptografado;
	private String tamanhoChave;
	private static Criptografia params;
	
	@Inject
	private CriptografiaUtil criptografiaUtil;

	@Inject
	private MessageContext messageContext;
	
	@PostConstruct
	public void init() {
		listaAlgoritmo = new ArrayList<SelectItem>();
		for (GeradorChave item : GeradorChave.values()) {
			listaAlgoritmo.add(new SelectItem(item.name(), item.name()));
		}
		textoPuro = "Tudo posso naquele que me fortalece.";
	}

	public Criptografia criptografar() {
		Criptografia params = new Criptografia();
		try {
			params.setFrase(textoPuro.getBytes());
			params = criptografiaUtil.criptografiaSimetrica(GeradorChave.valueOf(algoritmo), params, true);

			textoCriptografado = params.getCriptografadoToHexadecimal();
			chaveUnica = Hex.encodeHexString(params.getChaveUnica().getEncoded());
			chaveUnicaFormato = params.getChaveUnica().getFormat();
			tamanhoChave = Integer.toString(params.getTamanhoChave());
		} catch (CriptografiaException e) {
			messageContext.add("ERRO: " + e.getMessage());
		}
		
		return params;
	}

	public void descriptografar(Criptografia params) {
		try {
			params = criptografiaUtil.criptografiaSimetrica(GeradorChave.valueOf(algoritmo), params, false);

			textoDescriptografado = params.getDescriptografadoToString();
			textoCriptografado = params.getCriptografadoToHexadecimal();
		} catch (CriptografiaException e) {
			messageContext.add("ERRO: " + e.getMessage());
		}
	}
	
	public void limpar() {
		algoritmo = GeradorChave.values()[0].toString();
		chaveUnica = "";
		chaveUnicaFormato = "";
		textoPuro = "";
		textoCriptografado = "";
		textoDescriptografado = "";
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public List<SelectItem> getListaAlgoritmo() {
		return listaAlgoritmo;
	}

	public void setListaAlgoritmo(List<SelectItem> listaAlgoritmo) {
		this.listaAlgoritmo = listaAlgoritmo;
	}

	public String getTextoPuro() {
		return textoPuro;
	}

	public void setTextoPuro(String textoPuro) {
		this.textoPuro = textoPuro;
	}

	public String getTextoCriptografado() {
		return textoCriptografado;
	}

	public void setTextoCriptografado(String textoCriptografado) {
		this.textoCriptografado = textoCriptografado;
	}

	public String getTextoDescriptografado() {
		return textoDescriptografado;
	}

	public void setTextoDescriptografado(String textoDescriptografado) {
		this.textoDescriptografado = textoDescriptografado;
	}

	public String getChaveUnica() {
		return chaveUnica;
	}

	public void setChaveUnica(String chaveUnica) {
		this.chaveUnica = chaveUnica;
	}

	public String getChaveUnicaFormato() {
		return chaveUnicaFormato;
	}

	public void setChaveUnicaFormato(String chaveUnicaFormato) {
		this.chaveUnicaFormato = chaveUnicaFormato;
	}

	public Criptografia getParams() {
		return params;
	}

	public void setParams(Criptografia params) {
		CriptografiaView.params = params;
	}

	public String getTamanhoChave() {
		return tamanhoChave;
	}

	public void setTamanhoChave(String tamanhoChave) {
		this.tamanhoChave = tamanhoChave;
	}

}
