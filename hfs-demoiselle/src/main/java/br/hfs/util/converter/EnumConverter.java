package br.hfs.util.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("enumConverter")
public class EnumConverter implements Converter {
	
	protected void addAttribute(UIComponent component, IEnum<?> o) {
		String key = "";
		if (o.getId() != null) {
			key = o.getId().toString();
		}
		getAttributesFrom(component).put(key, o);
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null) {
			return getAttributesFrom(component).get(value);
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String strValue = null;
		if ((value != null) && (!"".equals(value))) {
			IEnum<?> ienum = (IEnum<?>) value;
			addAttribute(component, ienum);

			Long codigo = (Long) ienum.getId();
			if (codigo != null) {
				return String.valueOf(codigo);
			}
		}
		return strValue;
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}
}
