package br.hfs.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="bsUpperConverter", forClass=String.class)
public class StringTrimUpperConverter
  extends StringTrimConverter
{
  public Object getAsObject(FacesContext context, UIComponent component, String value)
  {
    Object obj = super.getAsObject(context, component, value);
    if (obj != null) {
      obj = ((String)obj).toUpperCase();
    }
    return obj;
  }
  
  public String getAsString(FacesContext context, UIComponent component, Object value)
  {
    String string = super.getAsString(context, component, value);
    if (string != null) {
      string = string.toUpperCase();
    }
    return string;
  }
}
