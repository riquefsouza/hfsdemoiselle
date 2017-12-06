 package br.hfs.util.validator;
 
 import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.faces.util.MessageFactory;
 
 @FacesValidator("cpfValidator")
 public class CpfValidator
   implements Validator
 {
   public static final String CPF_INVALIDO = "validator.cpf.invalido";
  
   public void validate(FacesContext context, UIComponent component, Object value)
     throws ValidatorException
  {
    if (ValidatorUtil.check(context))
    {
       if ((context == null) || (component == null)) {
         throw new NullPointerException();
       }
      if (value != null)
       {
         String converted = String.valueOf(value);
        converted = converted.replaceAll("_", "");
         boolean validacaoCPF = ValidatorUtil.validacaoCPF(converted, true);
         if (!validacaoCPF) {
           throw new ValidatorException(MessageFactory.getMessage(context, "validator.cpf.invalido", new Object[] { converted, 
             MessageFactory.getLabel(context, component) }));
         }
       }
     }
   }
 }
