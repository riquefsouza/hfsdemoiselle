package br.hfs.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "stringTrimConverter", forClass = String.class)
public class StringTrimConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		String obj = null;
		if ((value != null) && ((value instanceof String))) {
			obj = ConverterUtil.itrim(value);
			if (obj.isEmpty()) {
				obj = null;
			}
		}
		return obj;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String obj = null;
		if ((value != null) && ((value instanceof String))) {
			obj = ConverterUtil.itrim((String) value);
			if (obj.isEmpty()) {
				obj = null;
			}
		}
		return obj;
	}
}
