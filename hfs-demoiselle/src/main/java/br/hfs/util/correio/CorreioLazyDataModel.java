package br.hfs.util.correio;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.hfs.util.StringUtil;

public class CorreioLazyDataModel extends LazyDataModel<Correio> {
	private static final long serialVersionUID = 1L;

	private List<Correio> datasource;
	
	private int dataSize; 

	public CorreioLazyDataModel(int dataSize) {
		this.dataSize = dataSize;
	}

	@Override
	public Correio getRowData(String rowKey) {
		for (Correio usuario : datasource) {
			if (usuario.getId().toString().equals(rowKey))
				return usuario;
		}

		return null;
	}

	@Override
	public Object getRowKey(Correio usuario) {
		return usuario.getId();
	}

	@Override
	public List<Correio> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		List<Correio> data = new ArrayList<Correio>();
		boolean bAchou = false;
		
		// paginate
		if (dataSize > pageSize) {			
			try {
				try {
					datasource = CorreioDAO.getInstancia().listarPorIntervalo(first+1, first + pageSize);
				} catch (IndexOutOfBoundsException e) {
					datasource = CorreioDAO.getInstancia().listarPorIntervalo(first+1, first + (dataSize % pageSize));
				}
			} catch (SQLException e) {
				return new ArrayList<Correio>();
			}
		}
		
		// filter
		for (Correio usuario : datasource) {
			boolean match = true;

			if (filters != null) {
				for (Iterator<String> it = filters.keySet().iterator(); it
						.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						
						Method metodo1 = usuario.getClass().getMethod(
								StringUtil.cap(filterProperty), new Class[] {});
						String fieldValue = String.valueOf(metodo1.invoke(usuario, new Object[] {}));

						if (filterValue == null
								|| fieldValue
										.startsWith(filterValue.toString())) {
							match = true;
							bAchou = true;
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
					}
				}
			}

			if (match) {
				data.add(usuario);
			}
		}

		// sort
		if (sortField != null) {
			Collections.sort(data, new CorreioLazySorter(sortField, sortOrder));
		}

		if (filters.keySet().size() > 0 && bAchou) {
			this.setRowCount(data.size());
		} else {
			this.setRowCount(dataSize);
		}
		
		return data;
	}
}