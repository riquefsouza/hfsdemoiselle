package br.hfs.view;

import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import br.gov.frameworkdemoiselle.certificate.criptography.DigestAlgorithmEnum;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractPageBean;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.hfs.business.UsuarioBC;
import br.hfs.domain.Usuario;
import br.hfs.security.Credenciais;
import br.hfs.util.security.SegurancaUtil;

@ViewController
public class AlterarSenhaMB extends AbstractPageBean {

	private static final long serialVersionUID = 1L;

	private String senhaAtual;
	private String senhaNova;
	private String confirmaSenhaNova;
	private String msgAlterarSenha;
	private boolean renderBotao;
	private String cabecalhoMsgAlterarSenha;

	@Inject
	private Credenciais credentials;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private UsuarioBC usuarioBC;
	
	@Inject
	private SegurancaUtil segurancaUtil;

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getConfirmaSenhaNova() {
		return confirmaSenhaNova;
	}

	public void setConfirmaSenhaNova(String confirmaSenhaNova) {
		this.confirmaSenhaNova = confirmaSenhaNova;
	}

	public String getMsgAlterarSenha() {
		return msgAlterarSenha;
	}

	public void setMsgAlterarSenha(String msgAlterarSenha) {
		this.msgAlterarSenha = msgAlterarSenha;
	}

	public boolean isRenderBotao() {
		return renderBotao;
	}

	public void setRenderBotao(boolean renderBotao) {
		this.renderBotao = renderBotao;
	}

	public String getCabecalhoMsgAlterarSenha() {
		return cabecalhoMsgAlterarSenha;
	}

	public void setCabecalhoMsgAlterarSenha(String cabecalhoMsgAlterarSenha) {
		this.cabecalhoMsgAlterarSenha = cabecalhoMsgAlterarSenha;
	}

	public void alterarSenhaUsuario() {
		String senha = "";
		renderBotao = false;

		if ((senhaNova == null && confirmaSenhaNova == null && senhaAtual == null)
				|| (senhaNova.equals("") && confirmaSenhaNova.equals("") && senhaAtual
						.equals(""))) {
			msgAlterarSenha = "Por favor, preencha todos os campos";
			RequestContext.getCurrentInstance().execute(
					"PF('dlgAlterarSenha').show();");

		} else if ((senhaNova == null && confirmaSenhaNova == null)
				|| (senhaNova.equals("") && confirmaSenhaNova.equals(""))) {
			msgAlterarSenha = "Por favor, preencha todos os campos";
			RequestContext.getCurrentInstance().execute(
					"PF('dlgAlterarSenha').show();");
		} else {

			if (senhaAtual.equals(credentials.getSenha())) {

				if (senhaNova.equals(confirmaSenhaNova)) {
					senha = segurancaUtil.HashSimplesHex(confirmaSenhaNova.getBytes(),
							DigestAlgorithmEnum.SHA_1);

					credentials.setSenha(senha);
					Usuario usuario = credentials.getUsuario();
					usuario.setSenha(senha);

					usuarioBC.update(usuario);

					credentials.setUsuario(usuario);

					renderBotao = false;
					msgAlterarSenha = bundle.getString("ms.msgSenhaAlterada");
					cabecalhoMsgAlterarSenha = "Sucesso";
					RequestContext.getCurrentInstance().execute(
							"PF('dlgAlterarSenha').show();");
				} else {
					renderBotao = true;
					msgAlterarSenha = bundle
							.getString("ms.msgSenhaNovaConfirmaSenhaNaoConfere");
					cabecalhoMsgAlterarSenha = "Erro";
					RequestContext.getCurrentInstance().execute(
							"PF('dlgAlterarSenha').show();");
				}
			} else {
				renderBotao = true;
				msgAlterarSenha = bundle
						.getString("ms.msgSenhaAtualNaoConfere");
				cabecalhoMsgAlterarSenha = "Erro";
				RequestContext.getCurrentInstance().execute(
						"PF('dlgAlterarSenha').show();");
			}

		}
	}
}