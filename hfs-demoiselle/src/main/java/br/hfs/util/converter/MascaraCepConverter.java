package br.hfs.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("mascaraCepConverter")
public class MascaraCepConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
		Object valor = null;
		if ((value != null) && (!value.isEmpty())) {
			valor = ConverterUtil.removerFormatacaoCEP(value);
		}
		return valor;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		String valor = (String) value;
		if ((valor != null) && (!valor.isEmpty())) {
			valor = ConverterUtil.adicionarFormatacaoCEP(valor);
		}
		return valor;
	}

}
