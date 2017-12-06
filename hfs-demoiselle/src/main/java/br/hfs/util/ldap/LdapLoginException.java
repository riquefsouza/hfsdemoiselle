package br.hfs.util.ldap;

public class LdapLoginException extends Exception {
	private static final long serialVersionUID = 1L;

	public LdapLoginException(String message) {
		super(message);
	}
}
