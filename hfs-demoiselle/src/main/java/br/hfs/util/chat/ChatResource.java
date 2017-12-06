package br.hfs.util.chat;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.push.EventBus;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PathParam;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PushEndpoint("/{room}/{user}")
@Singleton
public class ChatResource implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(ChatResource.class);

	@PathParam("room")
	private String room;

	@PathParam("user")
	private String username;

	@OnOpen
	public void onOpen(RemoteEndpoint r, EventBus eventBus) {
		logger.info("OnOpen {}", r);

		eventBus.publish(
				room + "/*",
				new ChatMessage(String.format("%s has entered the room '%s'",
						username, room), true));
	}

	@OnClose
	public void onClose(RemoteEndpoint r, EventBus eventBus) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext ctx = (ServletContext) fc.getExternalContext()
				.getContext();

		ChatUsers users = (ChatUsers) ctx.getAttribute("chatUsers");
		users.remove(username);

		eventBus.publish(room + "/*",
				new ChatMessage(
						String.format("%s has left the room", username), true));
	}

	@OnMessage(decoders = { ChatMessageDecoder.class }, encoders = { ChatMessageEncoder.class })
	public ChatMessage onMessage(ChatMessage message) {
		return message;
	}

}
