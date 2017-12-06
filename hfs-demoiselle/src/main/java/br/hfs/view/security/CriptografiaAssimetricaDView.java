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

import br.gov.frameworkdemoiselle.certificate.criptography.AsymmetricAlgorithmEnum;
import br.hfs.util.security.CriptografiaParams;
import br.hfs.util.security.SegurancaUtil;

@ManagedBean
@RequestScoped
public class CriptografiaAssimetricaDView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String algoritmo;
	private List<SelectItem> listaAlgoritmo;
	private String chavePublica;
	private String chavePublicaFormato;
	private String chavePrivada;
	private String chavePrivadaFormato;
	private String textoPuro;
	private String textoCriptografado;
	private String textoDescriptografado;	
	private static CriptografiaParams params;
	
	@Inject
	private SegurancaUtil segurancaUtil;

	@PostConstruct
	public void init() {
		listaAlgoritmo = new ArrayList<SelectItem>();
		for (AsymmetricAlgorithmEnum item : AsymmetricAlgorithmEnum.values()) {
			listaAlgoritmo.add(new SelectItem(item.getAlgorithm(), item
					.getAlgorithm()));
		}
		textoPuro = "Tudo posso naquele que me fortalece.";
	}

	public CriptografiaParams criptografar() {
		CriptografiaParams params = new CriptografiaParams();
		params.setFrase(textoPuro.getBytes());
		params.setAlgoritmoAssimetrico(AsymmetricAlgorithmEnum
				.getAsymmetricAlgorithmEnum(algoritmo));
		params = segurancaUtil.criptografiaAssimetrica(params, true);

		textoCriptografado = params.getCriptografadoToHexadecimal();
		chavePublica = Hex.encodeHexString(params.getChavePublica().getEncoded());
		chavePublicaFormato = params.getChavePublica().getFormat();
		chavePrivada = Hex.encodeHexString(params.getChavePrivada().getEncoded());
		chavePrivadaFormato = params.getChavePrivada().getFormat();
		
		return params;
	}

	public void descriptografar(CriptografiaParams params) {
		params = segurancaUtil.criptografiaAssimetrica(params, false);

		textoDescriptografado = params.getDescriptografadoToString();
		textoCriptografado = params.getCriptografadoToHexadecimal();
	}
	
	public void limpar() {
		algoritmo = AsymmetricAlgorithmEnum.values()[0].toString();
		chavePublica = "";
		chavePublicaFormato = "";
		chavePrivada = "";
		chavePrivadaFormato = "";
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

	public CriptografiaParams getParams() {
		return params;
	}

	public void setParams(CriptografiaParams params) {
		CriptografiaAssimetricaDView.params = params;
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

}
