package br.hfs.domain.lazydatamodel;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.hfs.domain.Usuario;
import br.hfs.util.StringUtil;

public class UsuarioLazySorter implements Comparator<Usuario> {

	private String sortField;

	private SortOrder sortOrder;

	public UsuarioLazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	@SuppressWarnings("unchecked")
	public int compare(Usuario usuario1, Usuario usuario2) {
		try {
			Method metodo1 = usuario1.getClass().getMethod(
					StringUtil.cap(this.sortField), new Class[] {});
			Object value1 = metodo1.invoke(usuario1, new Object[] {});

			Method metodo2 = usuario2.getClass().getMethod(
					StringUtil.cap(this.sortField), new Class[] {});
			Object value2 = metodo2.invoke(usuario2, new Object[] {});
			
			int value = ((Comparable<Object>) value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
}
