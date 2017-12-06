package br.hfs.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.gov.frameworkdemoiselle.util.Beans;
import br.hfs.domain.EntidadePerfil;
import br.hfs.domain.EntidadePerfilPK;
import br.hfs.persistence.EntidadePerfilDAO;

@FacesConverter(value= "ConversorEntidadePerfil")
public class EntidadePerfilConverter implements Converter {

	private EntidadePerfilDAO entidadePerfilDAO = Beans.getReference(EntidadePerfilDAO.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try{
		Object ret = null;
			if (component instanceof PickList) {
				Object dualList = ((PickList) component).getValue();
				DualListModel<?> dl = (DualListModel<?>) dualList;
				for (Object o : dl.getSource()) {
					String id = String.valueOf(((EntidadePerfil) o).getId());
					if (value.equals(id)) {
						ret = o;
						break;
					}
				}
				if (ret == null)
					for (Object o : dl.getTarget()) {
						String id = String.valueOf(((EntidadePerfil) o).getId());
						if (value.equals(id)) {
							ret = o;
							break;
						}
					}
			} else {
				if (value.trim().equals("")) {
					ret = null;
				} else {
					Integer entidadeId = Integer.valueOf(value);
					Integer perfilId = Integer.valueOf(value);
					EntidadePerfilPK chave = new EntidadePerfilPK(entidadeId, perfilId);
					ret =  entidadePerfilDAO.load(chave);
				}
			}
			return ret;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		try{
			if (value == null || value.equals("")) {
				return "";
			} else {			        
				return String.valueOf(((EntidadePerfil) value).getId());
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}		
	}
}
