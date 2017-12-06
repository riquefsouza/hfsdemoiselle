package br.hfs;

import static br.gov.frameworkdemoiselle.annotation.Priority.MAX_PRIORITY;
import static br.gov.frameworkdemoiselle.annotation.Priority.MIN_PRIORITY;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.Priority;
import br.gov.frameworkdemoiselle.lifecycle.Shutdown;
import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.hfs.business.EntidadeBC;
import br.hfs.business.EntidadePerfilBC;
import br.hfs.business.PerfilBC;
import br.hfs.business.PermissaoBC;
import br.hfs.business.UsuarioBC;
import br.hfs.util.correio.CorreioDAO;
import br.hfs.util.jdbc.EstruturaDAO;
import br.hfs.util.jdbc.JdbcUtil;

public class Aplicacao {

	@Inject
	private Logger logger;

	@Inject
	private PerfilBC perfilBC;

	@Inject
	private UsuarioBC usuarioBC;

	@Inject
	private EntidadeBC entidadeBC;

	@Inject
	private PermissaoBC permissaoBC;

	@Inject
	private EntidadePerfilBC entidadePerfilBC;

	@Inject
	private AplicacaoConfig config;

	@Inject
	private JdbcUtil jdbcUtil;

	public Aplicacao() {
		super();
	}

	@Startup
	@Priority(MAX_PRIORITY)
	public void startup() {
		logger.info("******** INICIANDO: Sistema Exemplo ********");

		perfilBC.load();
		usuarioBC.load();
		entidadeBC.load();
		entidadePerfilBC.load();
		permissaoBC.load();

		try {
			EstruturaDAO.setConexao(jdbcUtil.Conectar(config.getDatabaseDriver(),
					config.getEstruturaURL(), config.getDatabaseLogin(),
					config.getDatabaseSenha()));
			
			CorreioDAO.setConexao(jdbcUtil.Conectar(config.getDatabaseDriver(),
					config.getCorreiosURL(), config.getDatabaseLogin(),
					config.getDatabaseSenha()));
		} catch (ClassNotFoundException e) {
			logger.severe("ERRO Driver JDBC" + e.getMessage());
		} catch (SQLException e) {
			logger.severe("ERRO Conectar JDBC" + e.getMessage());
		}
	}

	@Shutdown
	@Priority(MIN_PRIORITY)
	public void shutdown() {
		logger.info("******** FINALIZANDO: Sistema Exemplo ********");
		
		try {
			jdbcUtil.Desconectar(EstruturaDAO.getConexao());
			jdbcUtil.Desconectar(CorreioDAO.getConexao());
		} catch (SQLException e) {
			logger.severe("ERRO Desconectar JDBC" + e.getMessage());
		}
	}

}