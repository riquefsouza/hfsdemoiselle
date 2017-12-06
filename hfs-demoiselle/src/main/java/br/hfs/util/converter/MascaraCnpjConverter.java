package br.hfs.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("mascaraCnpjConverter")
public class MascaraCnpjConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Object valor = null;
		if ((value != null) && (!value.isEmpty())) {
			valor = ConverterUtil.removerFormatacaoCNPJ(value);
		}
		return valor;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String valor = (String) value;
		if ((valor != null) && (!valor.isEmpty())) {
			valor = ConverterUtil.adicionarFormatacaoCNPJ(valor);
		}
		return valor;
	}
}
