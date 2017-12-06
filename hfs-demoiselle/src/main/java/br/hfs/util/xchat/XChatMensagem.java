package br.hfs.util.xchat;

import java.io.Serializable;

import org.jxmpp.jid.EntityJid;

public class XChatMensagem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private EntityJid usuarioOrigem;

	private EntityJid usuarioDestino;

	private String assunto;

	private String mensagem;

	public XChatMensagem() {
		//this.usuarioOrigem = "";
		//this.usuarioDestino = "";
		this.assunto = "";
		this.mensagem = "";
	}

	public XChatMensagem(EntityJid usuarioOrigem, EntityJid usuarioDestino,
			String assunto, String mensagem) {
		this.usuarioOrigem = usuarioOrigem;
		this.usuarioDestino = usuarioDestino;
		this.assunto = assunto;
		this.mensagem = mensagem;

	}

	public EntityJid getUsuarioOrigem() {
		return usuarioOrigem;
	}

	public void setUsuarioOrigem(EntityJid usuarioOrigem) {
		this.usuarioOrigem = usuarioOrigem;
	}

	public EntityJid getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(EntityJid usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String toString() {
		return "Usuário Origem: " + this.usuarioOrigem + ", Usuário Destino: "
				+ this.usuarioDestino + ", Assunto:" + this.assunto
				+ ", Mensagem: " + this.mensagem;

	}
}
