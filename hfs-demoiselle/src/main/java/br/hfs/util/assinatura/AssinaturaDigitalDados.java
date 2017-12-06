package br.hfs.util.assinatura;

public class AssinaturaDigitalDados {
	
	private String mensagem;

	private String assinatura;

	private byte[] assinaturaEmByte;

	private String arquivoProperties;

	private String arquivoChavePublica;

	boolean assinaturaValida;

	public AssinaturaDigitalDados() {
		this.limparDados();
		arquivoProperties = "dadosAssinados.properties";
		arquivoChavePublica = "pubkey.ser";
	}

	public void limparDados() {
		mensagem = "";
		assinatura = "";
		arquivoProperties = "";
		arquivoChavePublica = "";
		assinaturaValida = false;
	}

	public String getArquivoChavePublica() {
		return arquivoChavePublica;
	}

	public void setArquivoChavePublica(String arquivoChavePublica) {
		this.arquivoChavePublica = arquivoChavePublica;
	}

	public String getArquivoProperties() {
		return arquivoProperties;
	}

	public void setArquivoProperties(String arquivoProperties) {
		this.arquivoProperties = arquivoProperties;
	}

	public String getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(String assinatura) {
		this.assinatura = assinatura;
	}

	public boolean isAssinaturaValida() {
		return assinaturaValida;
	}

	public void setAssinaturaValida(boolean assinaturaValida) {
		this.assinaturaValida = assinaturaValida;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public byte[] getAssinaturaEmByte() {
		return assinaturaEmByte;
	}

	public void setAssinaturaEmByte(byte[] assinaturaEmByte) {
		this.assinaturaEmByte = assinaturaEmByte;
	}

}
