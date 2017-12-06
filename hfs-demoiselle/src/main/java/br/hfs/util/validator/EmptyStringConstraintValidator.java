package br.hfs.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmptyStringConstraintValidator implements
		ConstraintValidator<NotEmpty, String> {
	public void initialize(NotEmpty constraintAnnotation) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean retorno = false;
		retorno = (value != null) && (!value.trim().isEmpty());
		return retorno;
	}
}
