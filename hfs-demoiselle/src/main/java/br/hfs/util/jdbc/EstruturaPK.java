package br.hfs.util.jdbc;

import java.io.Serializable;

public class EstruturaPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private int codigoOrganizacao;

	private int codigo;

	public EstruturaPK() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EstruturaPK) {
			EstruturaPK estruturaPK = (EstruturaPK) obj;

			if (estruturaPK.getCodigoOrganizacao() != codigoOrganizacao) {
				return false;
			}

			if (estruturaPK.getCodigo() != codigo) {
				return false;
			}

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return new Integer(codigoOrganizacao).hashCode()
				+ new Integer(codigo).hashCode();
	}

	public int getCodigoOrganizacao() {
		return codigoOrganizacao;
	}

	public void setCodigoOrganizacao(int codigoOrganizacao) {
		this.codigoOrganizacao = codigoOrganizacao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
