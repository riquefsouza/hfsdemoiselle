package br.hfs.view.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Hex;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.certificate.criptography.DigestAlgorithmEnum;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.hash.HashException;
import br.hfs.util.hash.HashUtil;

@ManagedBean
public class HashSimplesView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String algoritmo;
	private List<SelectItem> listaAlgoritmo;
	private String textoPuro;
	private String textoHash;
	private UploadedFile arquivo;

	@Inject
	private HashUtil hashUtil;

	@Inject
	private MessageContext messageContext;
	
	@PostConstruct
	public void init() {
		listaAlgoritmo = new ArrayList<SelectItem>();
		for (String item : hashUtil.listarAlgoritmosHash()) {
			listaAlgoritmo.add(new SelectItem(item, item));
		}
		textoPuro = "Tudo posso naquele que me fortalece.";
		textoHash = "";
	}

	public void gerar() {
		try {
			textoHash = Hex.encodeHexString(hashUtil.getHash(algoritmo,
					textoPuro.getBytes()));
		} catch (HashException e) {
			messageContext.add("ERRO: " + e.getMessage());
		}
	}

	public void gerarArquivo() {
		try {
			textoPuro = "";
			textoHash = Hex.encodeHexString(hashUtil.getHash(algoritmo,
					arquivo.getContents()));
		} catch (HashException e) {
			messageContext.add("ERRO: " + e.getMessage());
		}
	}

	public void limpar() {
		algoritmo = DigestAlgorithmEnum.values()[0].toString();
		textoPuro = "";
		textoHash = "";
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

	public String getTextoHash() {
		return textoHash;
	}

	public void setTextoHash(String textoHash) {
		this.textoHash = textoHash;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

}
