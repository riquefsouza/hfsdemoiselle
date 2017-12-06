package br.hfs.util.report;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.hfs.domain.Usuario;

public class UsuarioJRDS implements JRDataSource {

	private Usuario objeto;

	private Iterator<Usuario> iterator;

	public UsuarioJRDS(List<Usuario> lista) {
		this.iterator = lista.iterator();
	}

	@Override
	public Object getFieldValue(JRField campo) throws JRException {
		Object valor = null;

		if ("login".equals(campo.getName())) {
			valor = objeto.getLogin();
		} else if ("nome".equals(campo.getName())) {
			valor = objeto.getNome();
		} else if ("email".equals(campo.getName())) {
			valor = objeto.getEmail();
		}

		return valor;
	}

	@Override
	public boolean next() throws JRException {
		objeto = iterator.hasNext() ? iterator.next() : null;
		return (objeto != null);
	}

}
