package br.hfs.util.xchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.EntityJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;

public final class XChatUtil {
	private static Logger log = Logger.getLogger("ChatUtil");

	private XChatMensagem mensagem;
	private AbstractXMPPConnection conexao;

	public AbstractXMPPConnection getConexao() {
		return conexao;
	}

	public void conecta(String host, int porta, XChatUsuario usuario)
			throws XChatException {
		try {
			XMPPTCPConnectionConfiguration.Builder cfg = XMPPTCPConnectionConfiguration
					.builder();
			cfg.setUsernameAndPassword(usuario.getUsuario(), usuario.getSenha());
			DomainBareJid serviceDomain = JidCreate
					.domainBareFrom("msg.trt1.jus.br");
			cfg.setXmppDomain(serviceDomain);			
			cfg.setHost(host);
			cfg.setPort(porta);

			/*
			String pHost = "bravo.trtrio.gov.br";
			int pPort = 8080;
			String pUser = "henrique.souza";
			String pPass = "";
			
			ProxyInfo proxyInfo = new ProxyInfo(ProxyType.HTTP, pHost, pPort,
					pUser, pPass);

			cfg.setProxyInfo(proxyInfo);
			*/
			//cfg.setDebuggerEnabled(true);
			// cfg.setCompressionEnabled(true);
			cfg.setSendPresence(true);
			cfg.setSecurityMode(SecurityMode.disabled);

			//SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
			//SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
			//SASLAuthentication.unBlacklistSASLMechanism("PLAIN");

			conexao = new XMPPTCPConnection(cfg.build());
			conexao.connect();
			conexao.login();
			
			conexao.addConnectionListener(new ConnectionListener() {
				
				@Override
				public void reconnectionSuccessful() {
					log.info("reconnectionSuccessful()");					
				}
				
				@Override
				public void reconnectionFailed(Exception e) {
					log.info("reconnectionFailed: " + e.getMessage());
					
				}
				
				@Override
				public void reconnectingIn(int em) {
					log.info("reconnectingIn: " + em);					
				}
				
				@Override
				public void connectionClosedOnError(Exception e) {
					log.info("connectionClosedOnError: " + e.getMessage());					
				}
				
				@Override
				public void connectionClosed() {
					log.info("connectionClosed()");
					
				}
				
				@Override
				public void connected(XMPPConnection conn) {
					log.info("XMPPConnection: " + conn);
					
				}
				
				@Override
				public void authenticated(XMPPConnection conn, boolean autenticado) {
					log.info("authenticated: " + autenticado);
				}
			}); 
				
		} catch (SmackException e) {
			throw new XChatException(log, e.getMessage());
		} catch (IOException e) {
			throw new XChatException(log, e.getMessage());
		} catch (XMPPException e) {
			throw new XChatException(log, e.getMessage());
		} catch (InterruptedException e) {
			throw new XChatException(log, e.getMessage());
		}
	}

	private Chat criarChat(EntityJid usuario) {
		ChatManager chatManager = ChatManager.getInstanceFor(conexao);
		Chat chat = chatManager.createChat(usuario, new ChatMessageListener() {
			@Override
			public void processMessage(Chat chat, Message message) {
				mensagem.setUsuarioOrigem(message.getFrom()
						.asEntityJidIfPossible());
				mensagem.setUsuarioDestino(message.getTo()
						.asEntityJidIfPossible());
				mensagem.setAssunto(message.getSubject());
				mensagem.setMensagem(message.getBody());
				log.info("Chat Participante: " + chat.getParticipant()
						+ ", Mensagem recebida -> " + mensagem.toString());
			}
		});
		return chat;
	}

	public void enviarMensagem(EntityJid usuarioDestino, String mensagem)
			throws XChatException {
		try {
			Chat chat = this.criarChat(usuarioDestino);
			chat.sendMessage(mensagem);
		} catch (NotConnectedException e) {
			throw new XChatException(log, "Erro ao enviar mensagem pelo chat. "
					+ e.getMessage());
		} catch (InterruptedException e) {
			throw new XChatException(log, "Erro ao enviar mensagem pelo chat. "
					+ e.getMessage());
		}
	}

	public void enviarMensagem(EntityJid usuarioOrigem, XChatMensagem mensagem)
			throws XChatException {
		try {
			Chat chat = this.criarChat(usuarioOrigem);

			Message mens = new Message();
			mens.setFrom(mensagem.getUsuarioOrigem());
			mens.setTo(mensagem.getUsuarioDestino());
			mens.setSubject(mensagem.getAssunto());
			mens.setBody(mensagem.getMensagem());

			chat.sendMessage(mens);
		} catch (NotConnectedException e) {
			throw new XChatException(log, "Erro ao enviar mensagem pelo chat. "
					+ e.getMessage());
		} catch (InterruptedException e) {
			throw new XChatException(log, "Erro ao enviar mensagem pelo chat. "
					+ e.getMessage());
		}
	}

	public List<XChatUsuario> getUsuarios() {
		Roster roster = Roster.getInstanceFor(conexao);
		Collection<RosterEntry> entries = roster.getEntries();
		List<XChatUsuario> usuarios = new ArrayList<XChatUsuario>();
		for (RosterEntry entry : entries) {
			usuarios.add(new XChatUsuario(entry.getName(), entry.getJid()
					.asUnescapedString()));
		}
		roster.addRosterListener(new RosterListener() {
			public void entriesAdded(Collection<Jid> id) {
				log.info("Usuários adicionados: " + id.toString());
			}

			public void entriesDeleted(Collection<Jid> id) {
				log.info("Usuários excluídos: " + id.toString());
			}

			public void entriesUpdated(Collection<Jid> id) {
				log.info("Usuários atualizados: " + id.toString());
			}

			public void presenceChanged(Presence presence) {
				log.info("Presence modificado: " + presence.getFrom() + " " + presence);
			}
		});
		return usuarios;
	}

	public void desconecta() {
		conexao.disconnect();
	}

	public XChatMensagem getMensagem() {
		return mensagem;
	}

}
