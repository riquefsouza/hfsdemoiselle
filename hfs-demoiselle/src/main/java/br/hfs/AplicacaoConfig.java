package br.hfs;

import br.gov.frameworkdemoiselle.configuration.Configuration;

@Configuration(resource = "aplicacao")
public class AplicacaoConfig {

	private boolean verLogSessao;
	private int tempoMaxInativoSessao;
	private String databaseDriver;	
	private String databaseLogin;
	private String databaseSenha;
	private String estruturaURL;
	private String correiosURL;

	public String getDatabaseDriver() {
		return databaseDriver;
	}

	public String getDatabaseLogin() {
		return databaseLogin;
	}

	public String getDatabaseSenha() {
		return databaseSenha;
	}

	public boolean isVerLogSessao() {
		return verLogSessao;
	}

	public int getTempoMaxInativoSessao() {
		return tempoMaxInativoSessao;
	}

	public String getEstruturaURL() {
		return estruturaURL;
	}

	public String getCorreiosURL() {
		return correiosURL;
	}

}
