package br.hfs.util.ldap;

public class LdapExemplo {

	public static void main(String[] args) {
		try {
			LdapUtil ldap = new LdapUtil();		
			LdapConfig config = new LdapConfig();
			
			config.setLdapSPorta(636);
			config.setLdapSProtocolo("SSLv3");
			config.setLdapPorta(389);
			config.setLdapTipoConexao("LDAP");
			config.setLdapFilter("(&(objectCategory=person)(sAMAccountName=USER)(!(useraccountcontrol=514))(!(useraccountcontrol=546))(!(useraccountcontrol=66050)))");
			config.setLdapDnInativos("OU=INATIVOS,OU=TRT,DC=testedom,DC=jus,DC=br");
			//config.setLdapAtributoCodFuncional("description");
			config.setLdapAtributoCodFuncional("description,userPrincipalName,objectCategory,name,memberOf,mail,distinguishedName,displayName,cn,logonCount");		
			config.setLdapServer("trtrio.gov.br");
			config.setLdapUserSenha("C0nsult@2015");
			config.setLdapBaseDN("OU=TRT,DC=trtrio,DC=gov,DC=br");
			config.setLdapUserDN("CN=consultapauta,OU=CGNC,OU=STI,OU=TRT,DC=trtrio,DC=gov,DC=br");		
			
			ldap.configurar(config);
			
			//System.out.println(ldap.loginUsuario("henrique.souza", ""));
			
			for (LdapAtributo item: ldap.getAtributos("henrique.souza")) {
				System.out.println(item.getId() + " - " + item.getValor());			
			}
		} catch (LdapConfigurationException e) {
			e.printStackTrace();
		} catch (LdapLoginException e) {
			e.printStackTrace();
		}

	}

}
