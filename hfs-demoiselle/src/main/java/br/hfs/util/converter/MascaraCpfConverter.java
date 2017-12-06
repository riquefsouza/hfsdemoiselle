package br.hfs.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("mascaraCpfConverter")
public class MascaraCpfConverter implements Converter {
	
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
		Object valor = null;
		if ((value != null) && (!value.isEmpty())) {
			valor = ConverterUtil.removerFormatacaoCPF(value);
		}
		return valor;
	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		String valor = (String) value;
		if ((valor != null) && (!valor.isEmpty())) {
			valor = ConverterUtil.adicionarFormatacaoCPF(valor);
		}
		return valor;
	}
}
