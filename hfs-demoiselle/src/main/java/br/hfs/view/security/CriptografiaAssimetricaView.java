package br.hfs.view.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Hex;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.criptografia.Criptografia;
import br.hfs.util.criptografia.CriptografiaBCUtil;
import br.hfs.util.criptografia.CriptografiaException;
import br.hfs.util.criptografia.CriptografiaUtil.GeradorParChaves;

@ManagedBean
@RequestScoped
public class CriptografiaAssimetricaView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String algoritmo;
	//private List<SelectItem> listaAlgoritmo;
	private String chavePublica;
	private String chavePublicaFormato;
	private String chavePrivada;
	private String chavePrivadaFormato;
	private String textoPuro;
	private String textoCriptografado;
	private String textoDescriptografado;
	private String tamanhoChave;
	private static Criptografia params;

	@Inject
	private CriptografiaBCUtil criptografiaBCUtil;

	@Inject
	private MessageContext messageContext;

	@PostConstruct
	public void init() {
		/*
		listaAlgoritmo = new ArrayList<SelectItem>();
		for (String item : criptografiaUtil.listarGeradoresParChaves()) {
			listaAlgoritmo.add(new SelectItem(item, item));
		}
		*/
		algoritmo = "RSA";
		textoPuro = "Tudo posso naquele que me fortalece.";
	}

	public Criptografia criptografar() {
		Criptografia params = new Criptografia();
		params.setFrase(textoPuro.getBytes());
		try {
			params = criptografiaBCUtil.criptoAssimetrico(params, GeradorParChaves.RSA);

			textoCriptografado = params.getCriptografadoToHexadecimal();
			chavePublica = Hex.encodeHexString(params.getChavePublica()
					.getEncoded());
			chavePublicaFormato = params.getChavePublica().getFormat();
			chavePrivada = Hex.encodeHexString(params.getChavePrivada()
					.getEncoded());
			chavePrivadaFormato = params.getChavePrivada().getFormat();
			textoDescriptografado = params.getDescriptografadoToString();
			tamanhoChave = Integer.toString(params.getTamanhoChave());
		} catch (CriptografiaException e) {
			messageContext.add("ERRO: " + e.getMessage());
		}
		return params;
	}

	public void limpar() {
		chavePublica = "";
		chavePublicaFormato = "";
		chavePrivada = "";
		chavePrivadaFormato = "";
		textoPuro = "Tudo posso naquele que me fortalece.";
		textoCriptografado = "";
		textoDescriptografado = "";
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
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

	public Criptografia getParams() {
		return params;
	}

	public void setParams(Criptografia params) {
		CriptografiaAssimetricaView.params = params;
	}

	public String getChavePublica() {
		return chavePublica;
	}

	public void setChavePublica(String chavePublica) {
		this.chavePublica = chavePublica;
	}

	public String getChavePublicaFormato() {
		return chavePublicaFormato;
	}

	public void setChavePublicaFormato(String chavePublicaFormato) {
		this.chavePublicaFormato = chavePublicaFormato;
	}

	public String getChavePrivada() {
		return chavePrivada;
	}

	public void setChavePrivada(String chavePrivada) {
		this.chavePrivada = chavePrivada;
	}

	public String getChavePrivadaFormato() {
		return chavePrivadaFormato;
	}

	public void setChavePrivadaFormato(String chavePrivadaFormato) {
		this.chavePrivadaFormato = chavePrivadaFormato;
	}

	public String getTamanhoChave() {
		return tamanhoChave;
	}

	public void setTamanhoChave(String tamanhoChave) {
		this.tamanhoChave = tamanhoChave;
	}

}
