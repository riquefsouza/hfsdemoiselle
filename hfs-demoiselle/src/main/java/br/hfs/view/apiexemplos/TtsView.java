package br.hfs.view.apiexemplos;

import java.io.File;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.StringUtil;
import br.hfs.util.tts.TtsException;
import br.hfs.util.tts.TtsUtil;

@ManagedBean
public class TtsView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String texto;
	
	private String arquivo;

	@Inject
	private TtsUtil ttsUtil;

	@Inject
	private MessageContext messageContext;

	@PostConstruct
	public void init(){
		texto = "my name is kevin";
	}
	
	public void falar() {
		if (!texto.isEmpty()) {
			String caminho = "/temp";
			String caminhoReal = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath(caminho);		
			String nomeArquivo = StringUtil.getArquivoRandom();
			
			arquivo = caminho + "/" + nomeArquivo + ".wav"; 
			
			try {
				ttsUtil.inicializarFreeTTS("kevin16");
				//ttsUtil.falarFreeTTS(texto);
				ttsUtil.textoParaAudioWAV(texto, caminhoReal + File.separatorChar + nomeArquivo);
				ttsUtil.terminarFreeTTS();
			} catch (TtsException e) {
				messageContext.add(e.getMessage());
			}
		} else {
			messageContext.add("Texto não pode ser vazio!");
		}
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

}
