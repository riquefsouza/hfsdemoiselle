package br.hfs.util.ldap;

import br.gov.frameworkdemoiselle.configuration.Configuration;

@Configuration(resource = "ldap")
public class LdapConfig {

	private int ldapSPorta;
	private String ldapSProtocolo;
	private int ldapPorta;
	private String ldapTipoConexao;
	private String ldapFilter;
	private String ldapDnInativos;
	private String ldapAtributos;
	private String ldapAtributoCodFuncional;
	private String ldapServer;
	private String ldapUserSenha;
	private String ldapBaseDN;
	private String ldapUserDN;

	public int getLdapSPorta() {
		return ldapSPorta;
	}

	public void setLdapSPorta(int ldapSPorta) {
		this.ldapSPorta = ldapSPorta;
	}

	public String getLdapSProtocolo() {
		return ldapSProtocolo;
	}

	public void setLdapSProtocolo(String ldapSProtocolo) {
		this.ldapSProtocolo = ldapSProtocolo;
	}

	public int getLdapPorta() {
		return ldapPorta;
	}

	public void setLdapPorta(int ldapPorta) {
		this.ldapPorta = ldapPorta;
	}

	public String getLdapTipoConexao() {
		return ldapTipoConexao;
	}

	public void setLdapTipoConexao(String ldapTipoConexao) {
		this.ldapTipoConexao = ldapTipoConexao;
	}

	public String getLdapFilter() {
		return ldapFilter;
	}

	public void setLdapFilter(String ldapFilter) {
		this.ldapFilter = ldapFilter;
	}

	public String getLdapDnInativos() {
		return ldapDnInativos;
	}

	public void setLdapDnInativos(String ldapDnInativos) {
		this.ldapDnInativos = ldapDnInativos;
	}

	public String getLdapAtributoCodFuncional() {
		return ldapAtributoCodFuncional;
	}

	public void setLdapAtributoCodFuncional(String ldapAtributoCodFuncional) {
		this.ldapAtributoCodFuncional = ldapAtributoCodFuncional;
	}

	public String getLdapServer() {
		return ldapServer;
	}

	public void setLdapServer(String ldapServer) {
		this.ldapServer = ldapServer;
	}

	public String getLdapUserSenha() {
		return ldapUserSenha;
	}

	public void setLdapUserSenha(String ldapUserSenha) {
		this.ldapUserSenha = ldapUserSenha;
	}

	public String getLdapBaseDN() {
		return ldapBaseDN;
	}

	public void setLdapBaseDN(String ldapBaseDN) {
		this.ldapBaseDN = ldapBaseDN;
	}

	public String getLdapUserDN() {
		return ldapUserDN;
	}

	public void setLdapUserDN(String ldapUserDN) {
		this.ldapUserDN = ldapUserDN;
	}

	public String getLdapAtributos() {
		return ldapAtributos;
	}

	public void setLdapAtributos(String ldapAtributos) {
		this.ldapAtributos = ldapAtributos;
	}
}
