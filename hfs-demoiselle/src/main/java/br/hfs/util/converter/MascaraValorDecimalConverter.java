package br.hfs.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("mascaraValorDecimalConverter")
public class MascaraValorDecimalConverter implements Converter {
	
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
		Object valor = null;
		if (value != null) {
			valor = ConverterUtil.converteMascaraValorParaDouble(value);
		}
		return valor;
	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		String valor = null;
		if (value != null) {
			valor = ConverterUtil
					.formatarDoubleParaMascaraValor((Double) value);
		}
		return valor;
	}
}
