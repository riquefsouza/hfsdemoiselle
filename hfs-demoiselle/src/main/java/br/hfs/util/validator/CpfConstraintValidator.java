package br.hfs.util.validator;

import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfConstraintValidator implements ConstraintValidator<CPF, String> {
	
	public void initialize(CPF constraintAnnotation) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean cpfValido = false;
		if (value == null) {
			cpfValido = true;
		} else if (!value.isEmpty()) {
			cpfValido = validaCPF(value);
		}
		return cpfValido;
	}

	public String retirarMascaraCpf(String cpf) {
		String cpfSemMascara = "";
		if (cpf != null) {
			cpfSemMascara = cpf.replaceAll("\\.|\\-", "");
		}
		return cpfSemMascara;
	}

	private String validaCalculoDigitoVerificador(String num) {
		int soma = 0;
		int peso = 10;
		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}

		Integer primDig;
		if (((soma % 11 == 0 ? 1 : 0) | (soma % 11 == 1 ? 1 : 0)) != 0) {
			primDig = new Integer(0);
		} else {
			primDig = new Integer(11 - soma % 11);
		}
		soma = 0;
		peso = 11;
		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}
		soma += primDig.intValue() * 2;

		Integer segDig;
		if (((soma % 11 == 0 ? 1 : 0) | (soma % 11 == 1 ? 1 : 0)) != 0) {
			segDig = new Integer(0);
		} else {
			segDig = new Integer(11 - soma % 11);
		}
		return primDig.toString() + segDig.toString();
	}

	public boolean validaCPF(String cpf) {
		String cpfValidado = cpf;
		if (cpf.length() == 14) {
			cpfValidado = retirarMascaraCpf(cpf);
		}
		if (cpfValidado.length() != 11) {
			return false;
		}
		if (validaCpfInvalidos(cpfValidado)) {
			return false;
		}
		String numDig = cpfValidado.substring(0, 9);
		return validaCalculoDigitoVerificador(numDig).equals(
				cpfValidado.substring(9, 11));
	}

	public boolean validaCpfInvalidos(String cpfSemMascara) {
		List<String> cpfInvalidos = Arrays.asList(new String[] { "00000000000",
				"11111111111", "22222222222", "33333333333", "44444444444",
				"55555555555", "66666666666", "77777777777", "88888888888",
				"99999999999" });
		return cpfInvalidos.contains(cpfSemMascara);
	}
}
