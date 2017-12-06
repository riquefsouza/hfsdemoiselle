package br.hfs.view.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.certificate.criptography.DigestAlgorithmEnum;
import br.hfs.util.security.SegurancaUtil;

@ManagedBean
public class HashSimplesDView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String algoritmo;
	private List<SelectItem> listaAlgoritmo;
	private String textoPuro;
	private String textoHash;
	private UploadedFile arquivo;
	
	@Inject
	private SegurancaUtil segurancaUtil;
	
	@PostConstruct
	public void init() {
		listaAlgoritmo = new ArrayList<SelectItem>();
		for (DigestAlgorithmEnum item : DigestAlgorithmEnum.values()) {
			listaAlgoritmo.add(new SelectItem(item.getAlgorithm(), item
					.getAlgorithm()));
		}
		textoPuro = "Tudo posso naquele que me fortalece.";
		textoHash = "";
	}

	public void gerar() {
		textoHash = segurancaUtil.HashSimplesHex(textoPuro.getBytes(),
				DigestAlgorithmEnum.getDigestAlgorithmEnum(algoritmo));
	}

	public void gerarArquivo() {
		textoPuro = "";
		textoHash = segurancaUtil.HashSimplesHex(arquivo.getContents(),
				DigestAlgorithmEnum.getDigestAlgorithmEnum(algoritmo));
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
