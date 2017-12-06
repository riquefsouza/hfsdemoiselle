package br.hfs.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

import br.hfs.util.validator.ValidatorUtil;

@FacesConverter("dateConverter")
public class DateConverter
  extends DateTimeConverter
{
  public Object getAsObject(FacesContext context, UIComponent component, String value)
  {
    Object obj = null;
    try
    {
      value = ConverterUtil.removerFormatacaoDataVazia(value);
      obj = super.getAsObject(context, component, value);
    }
    catch (ConverterException e)
    {
      if (ValidatorUtil.check()) {
        throw e;
      }
      obj = null;
    }
    return obj;
  }
}
