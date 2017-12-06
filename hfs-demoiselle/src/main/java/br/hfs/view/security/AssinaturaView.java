package br.hfs.view.security;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.assinatura.AssinaturaDigitalException;
import br.hfs.util.assinatura.AssinaturaDigitalUtil;
import br.hfs.util.assinatura.AssinaturaJNLP;

@ManagedBean
public class AssinaturaView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tipo = "jks";
	
	private UploadedFile arquivoStore;
	
	private UploadedFile arquivo;

	private StreamedContent saida;
	
	private StreamedContent jnlp;
	
	private String senha;

	@Inject
	private AssinaturaDigitalUtil adUtil;
	
	@Inject
	private AssinaturaJNLP jnlpUtil;

	@Inject
	private MessageContext messageContext;

	public StreamedContent getJnlp() {
		jnlp = new DefaultStreamedContent(new ByteArrayInputStream(jnlpUtil
				.getJNLP().getBytes()), "application/x-java-jnlp-file",
				"hfsAssinador.jnlp");

		return jnlp;
	}
	
	public StreamedContent getSaida() {
		if (arquivoStore != null && arquivoStore.getSize() > 0) {
		
			if (arquivo != null && arquivo.getSize() > 0) {
				try {
					//File nomeStore = new File(
					//		"C:/Demoiselle/workspace/hfs-demoiselle/keystore/KeyStore.jks");
					//byte[] dataStore = FileUtils.readFileToByteArray(nomeStore);
					//new ByteArrayInputStream(dataStore)
					
					boolean btipo = tipo.equals("jks") ? true : false;
					
					byte[] encodedPKCS7 = adUtil.assinarPKCS7(btipo,
							new ByteArrayInputStream(arquivoStore.getContents()), senha,
							arquivo.getContents());
	
					saida = new DefaultStreamedContent(new ByteArrayInputStream(
							encodedPKCS7), "application/pkcs7-signature",
							arquivo.getFileName() + ".p7s");
	
				} catch (AssinaturaDigitalException e) {
					messageContext.add("ERRO: " + e.getMessage());
				}
	
			} else {
				messageContext.add("Arquivo a ser assinado não selecionado!");
			}
			
		} else {
			messageContext.add("Arquivo Java Key Store (.jks) ou PKCS #12 (.pfx) não selecionado!");
		}
		
		return saida;
	}

	public UploadedFile getArquivoStore() {
		return arquivoStore;
	}

	public void setArquivoStore(UploadedFile arquivoStore) {
		this.arquivoStore = arquivoStore;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
