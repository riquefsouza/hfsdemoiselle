package br.hfs.util.ldap;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class LdapMB implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<LdapAtributo> lista;
	private String login;
	private String senha;
	
	@Inject
	MessageContext messageContext;

	@Inject
	LdapUtil ldap;

	public List<LdapAtributo> getLista() {
		return lista;
	}
	
	public void testarLogin() {
		try {
			messageContext.add(ldap.loginUsuario(login, senha));
			gerar();
			
		} catch (LdapConfigurationException lce) {
			messageContext.add(lce.getMessage());
		} catch (LdapLoginException le) {
			messageContext.add(le.getMessage());
		}			
	}
	
	public void gerar() {
		try {
			lista = ldap.getAtributos(login);
		} catch (LdapConfigurationException lce) {
			messageContext.add(lce.getMessage());
		} catch (LdapLoginException le) {
			messageContext.add(le.getMessage());
		}
	}
	
	@PostConstruct
	public void init(){
		login = "henrique.souza";
		gerar();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
