package br.hfs.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CNPJConstraintValidator implements
		ConstraintValidator<CNPJ, String> {
	
	private static boolean validarCNPJ(String cnpjStr) {
		int soma = 0;
		int digito = -1;

		String cnpj_calc = cnpjStr.substring(0, 12);
		if (cnpjStr.length() != 14) {
			return false;
		}
		char[] chr_cnpj = cnpjStr.toCharArray();
		for (int i = 0; i < 4; i++) {
			if ((chr_cnpj[i] - '0' >= 0) && (chr_cnpj[i] - '0' <= 9)) {
				soma += (chr_cnpj[i] - '0') * (6 - (i + 1));
			}
		}
		for (int i = 0; i < 8; i++) {
			if ((chr_cnpj[(i + 4)] - '0' >= 0)
					&& (chr_cnpj[(i + 4)] - '0' <= 9)) {
				soma += (chr_cnpj[(i + 4)] - '0') * (10 - (i + 1));
			}
		}
		digito = 11 - soma % 11;

		cnpj_calc = cnpj_calc
				+ ((digito == 10) || (digito == 11) ? "0" : Integer
						.toString(digito));

		soma = 0;
		for (int i = 0; i < 5; i++) {
			if ((chr_cnpj[i] - '0' >= 0) && (chr_cnpj[i] - '0' <= 9)) {
				soma += (chr_cnpj[i] - '0') * (7 - (i + 1));
			}
		}
		for (int i = 0; i < 8; i++) {
			if ((chr_cnpj[(i + 5)] - '0' >= 0)
					&& (chr_cnpj[(i + 5)] - '0' <= 9)) {
				soma += (chr_cnpj[(i + 5)] - '0') * (10 - (i + 1));
			}
		}
		digito = 11 - soma % 11;
		cnpj_calc = cnpj_calc
				+ ((digito == 10) || (digito == 11) ? "0" : Integer
						.toString(digito));

		return cnpjStr.equals(cnpj_calc);
	}

	public void initialize(CNPJ constraintAnnotation) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean cnpjValido = false;
		if ((value != null) && (!value.isEmpty())) {
			cnpjValido = validarCNPJ(retirarMascaraCNPJ(value));
		}
		return cnpjValido;
	}

	public String retirarMascaraCNPJ(String cnpj) {
		String cnpjSemMascara = "";
		if (cnpj != null) {
			cnpjSemMascara = cnpj.replaceAll("\\.|\\-|/", "");
		}
		return cnpjSemMascara;
	}
}
