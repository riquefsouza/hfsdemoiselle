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

import br.gov.frameworkdemoiselle.certificate.criptography.SymmetricAlgorithmEnum;
import br.hfs.util.security.CriptografiaParams;
import br.hfs.util.security.SegurancaUtil;

@ManagedBean
@RequestScoped
public class CriptografiaDView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String algoritmo;
	private List<SelectItem> listaAlgoritmo;
	private String chaveUnica;
	private String chaveUnicaFormato;
	private String textoPuro;
	private String textoCriptografado;
	private String textoDescriptografado;	
	private static CriptografiaParams params;
	
	@Inject
	private SegurancaUtil segurancaUtil;

	@PostConstruct
	public void init() {
		listaAlgoritmo = new ArrayList<SelectItem>();
		for (SymmetricAlgorithmEnum item : SymmetricAlgorithmEnum.values()) {
			listaAlgoritmo.add(new SelectItem(item.getAlgorithm(), item
					.getAlgorithm()));
		}
		textoPuro = "Tudo posso naquele que me fortalece.";
	}

	public CriptografiaParams criptografar() {
		CriptografiaParams params = new CriptografiaParams();
		params.setFrase(textoPuro.getBytes());
		params.setAlgoritmoSimetrico(SymmetricAlgorithmEnum
				.getSymmetricAlgorithm(algoritmo));
		params = segurancaUtil.criptografiaSimetrica(params, true);

		textoCriptografado = params.getCriptografadoToHexadecimal();
		chaveUnica = Hex.encodeHexString(params.getChaveUnica().getEncoded());
		chaveUnicaFormato = params.getChaveUnica().getFormat();
		
		return params;
	}

	public void descriptografar(CriptografiaParams params) {
		params = segurancaUtil.criptografiaSimetrica(params, false);

		textoDescriptografado = params.getDescriptografadoToString();
		textoCriptografado = params.getCriptografadoToHexadecimal();
	}
	
	public void limpar() {
		algoritmo = SymmetricAlgorithmEnum.values()[0].toString();
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

	public CriptografiaParams getParams() {
		return params;
	}

	public void setParams(CriptografiaParams params) {
		CriptografiaDView.params = params;
	}

}
