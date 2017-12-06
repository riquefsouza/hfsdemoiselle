package br.hfs.util.xchat;

import java.util.List;

public class XChatExemplo {

	public static void main(String[] args) {

		try {
			XChatUtil ct = new XChatUtil();
			XChatUsuario usu = new XChatUsuario();
			usu.setUsuario("henrique.souza@trtrio.gov.br");
			usu.setSenha("");
			ct.conecta("msg.trt1.jus.br", 5222, usu);

			if (ct.getConexao().isConnected()) {
				if (ct.getConexao().isAuthenticated()) {

					List<XChatUsuario> usuarios = ct.getUsuarios();

					for (XChatUsuario chatUsuario : usuarios) {
						System.out.println(chatUsuario.getNome() + " - "
								+ chatUsuario.getUsuario());

					}

					/*
					ct.enviarMensagem((EntityJid) JidCreate
							.fromUnescaped("leila.furtado@trtrio.gov.br"),
							"TESTANDO");
					 */
					//ct.desconecta();
				}
			}
		} catch (XChatException e) {
			e.printStackTrace();
		}
	}
}
