package br.hfs.util.correio;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.hfs.util.StringUtil;

public class CorreioLazySorter implements Comparator<Correio> {

	private String sortField;

	private SortOrder sortOrder;

	public CorreioLazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	@SuppressWarnings("unchecked")
	public int compare(Correio correio1, Correio correio2) {
		try {
			Method metodo1 = correio1.getClass().getMethod(
					StringUtil.cap(this.sortField), new Class[] {});
			Object value1 = metodo1.invoke(correio1, new Object[] {});

			Method metodo2 = correio2.getClass().getMethod(
					StringUtil.cap(this.sortField), new Class[] {});
			Object value2 = metodo2.invoke(correio2, new Object[] {});
			
			int value = ((Comparable<Object>) value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
}
