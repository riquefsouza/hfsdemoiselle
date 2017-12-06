package br.hfs.util.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.faces.util.MessageFactory;

@FacesValidator("cnpjValidator")
public class CnpjValidator implements Validator {
	public static final String CNPJ_INVALIDO = "validator.cnpj.invalido";

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (ValidatorUtil.check(context)) {
			if ((context == null) || (component == null)) {
				throw new NullPointerException();
			}
			if (value != null) {
				String converted = String.valueOf(value);
				converted = converted.replaceAll("_", "");
				boolean validacaoCNPJ = ValidatorUtil.validacaoCNPJ(converted);
				if (!validacaoCNPJ) {
					throw new ValidatorException(
							MessageFactory.getMessage(
									context,
									"validator.cnpj.invalido",
									new Object[] {
											converted,
											MessageFactory.getLabel(context,
													component) }));
				}
			}
		}
	}
}
