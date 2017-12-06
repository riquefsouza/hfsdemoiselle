package br.hfs.util.ldap;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

public class LdapUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	Logger logger;
	
	@Inject
	private LdapConfig config;

	public void configurar(LdapConfig config){
		this.config = config;
	}
	
	private LdapConnectionConfig configurarLDAP() {
		LdapConnectionConfig sslConfig = new LdapConnectionConfig();
		sslConfig.setLdapHost(config.getLdapServer());
		sslConfig.setLdapPort(getPortaLDAP());

		if ("LDAPS".compareTo(config.getLdapTipoConexao()) == 0) {
			sslConfig.setEnabledProtocols(new String[] { "SSLv2Hello", "SSLv3",
					"SSLv3" });
			sslConfig.setSslProtocol("TLSv1");
			sslConfig.setUseSsl(true);
		}
		return sslConfig;
	}

	private int getPortaLDAP() {
		if ("LDAP".compareTo(config.getLdapTipoConexao()) == 0) {
			return config.getLdapPorta();
		}

		if ("LDAPS".compareTo(config.getLdapTipoConexao()) == 0) {
			return config.getLdapSPorta();
		}

		return -1;
	}

	private void fecharConexaoLDAP(LdapConnection connection) 
			throws LdapConfigurationException {
		try {
			connection.close();
			logger.info("Conexão fechada LDAP");
		} catch (IOException e) {
			throw new LdapConfigurationException(e.getMessage());
		}
	}

	public String loginUsuario(String usuario, String senha)
			throws LdapConfigurationException, LdapLoginException {
		LdapConnectionConfig sslConfig = configurarLDAP();
		LdapNetworkConnection connection = new LdapNetworkConnection(sslConfig);
		String filter = config.getLdapFilter().replace("USER", usuario);
		String atributos[];

		atributos = new java.lang.String[] { config
				.getLdapAtributoCodFuncional() };

		String dnUsuario;
		String codFuncionalUsuario = "";
		boolean usuarioAutenticado = false;
		try {
			connection.bind(config.getLdapUserDN(), config.getLdapUserSenha());
		} catch (LdapException e) {
			e.printStackTrace();
			fecharConexaoLDAP(connection);
			throw new LdapConfigurationException(
					"Falha na comunicação com o servidor LDAP, verifique as configurações no ldap.properties");
		}
		try {
			EntryCursor cursor = connection.search(config.getLdapBaseDN(),
					filter, SearchScope.SUBTREE, atributos);
			Entry entry;
			if (cursor.next()) {
				entry = cursor.get();
				Dn dnInativos = new Dn(config.getLdapDnInativos());
				// Valida se o usuário não está inativo.
				if (dnInativos.isAncestorOf(entry.getDn())) {
					throw new LdapLoginException(
							"Falha de autenticação de usuário. Usuário está inativo.");
				}
				dnUsuario = entry.getDn().toString();
				codFuncionalUsuario = entry.get(
						config.getLdapAtributoCodFuncional()).getString();
				try {
					connection.unBind();
					connection.close();

					// Tenta autenticar com o usuário e senha
					connection = new LdapNetworkConnection(sslConfig);
					connection.bind(dnUsuario, senha);
					usuarioAutenticado = connection.isAuthenticated();
					connection.unBind();
					connection.close();

					logger.info("Usuário: " + usuario + " codFuncional: "
							+ codFuncionalUsuario + " Autenticado: "
							+ usuarioAutenticado);
					return codFuncionalUsuario;

				} catch (IOException e) {
					throw new LdapLoginException(
							"Falha ao encerrar a autenticação do usuário do sistema.");
				}
			} else {
				throw new LdapLoginException(
						"Falha de autenticação de usuário, verifique login e senha");

			}
		} catch (LdapException e) {
			throw new LdapLoginException(
					"Falha de autenticação de usuário, verifique login e senha");
		} catch (CursorException e) {
			throw new LdapConfigurationException(
					"Falha na busca no LDAP, verifique os parâmetros do ldap.properties");
		} finally {
			fecharConexaoLDAP(connection);
		}

	}

	public List<LdapAtributo> getAtributos(String usuario)
			throws LdapConfigurationException, LdapLoginException {
		List<LdapAtributo> lista = new ArrayList<LdapAtributo>();
		LdapConnectionConfig sslConfig = configurarLDAP();
		LdapNetworkConnection connection = new LdapNetworkConnection(sslConfig);
		String filter = config.getLdapFilter().replace("USER", usuario);
		String atributos[];

		atributos = config.getLdapAtributos().split(",");

		try {
			connection.bind(config.getLdapUserDN(), config.getLdapUserSenha());
		} catch (LdapException e) {
			fecharConexaoLDAP(connection);
			throw new LdapConfigurationException(
					"Falha na comunicação com o servidor LDAP, verifique as configurações no ldap.properties");
		}
		try {
			EntryCursor cursor = connection.search(config.getLdapBaseDN(),
					filter, SearchScope.SUBTREE, atributos);
			Entry entry;
			if (cursor.next()) {
				entry = cursor.get();
				Dn dnInativos = new Dn(config.getLdapDnInativos());
				// Valida se o usuário não está inativo.
				if (dnInativos.isAncestorOf(entry.getDn())) {
					throw new LdapLoginException(
							"Falha de autenticação de usuário. Usuário está inativo.");
				}
				
				for (Iterator<Attribute> iterator = entry.iterator(); iterator
						.hasNext();) {
					Attribute entrada = iterator.next();
					lista.add(new LdapAtributo(entrada.getId(), entrada.getString()));
				}				
				
			} else {
				throw new LdapLoginException(
						"Falha de autenticação de usuário, verifique login e senha");

			}
		} catch (LdapException e) {
			throw new LdapLoginException(
					"Falha de autenticação de usuário, verifique login e senha");
		} catch (CursorException e) {
			throw new LdapConfigurationException(
					"Falha na busca no LDAP, verifique os parâmetros do ldap.properties");
		} finally {
			fecharConexaoLDAP(connection);
		}
		
		return lista;
	}
	
}
