package br.hfs.util.validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import br.hfs.util.converter.ConverterUtil;

public class ValidatorUtil {
	// private static final String VALIDATE = "VALIDATE";
	public static final String KEY_VALIDAR = "NAO_VALIDAR";

	private static List<String> CPFS_INVALIDOS;

	public static boolean check() {
		return check(FacesContext.getCurrentInstance());
	}

	public static boolean check(FacesContext context) {
		ExternalContext externalContext = context.getExternalContext();
		Object validate = externalContext.getRequestMap().get("VALIDATE");
		if (validate != null) {
			return ((Boolean) validate).booleanValue();
		}
		for (Map.Entry<String, String[]> requestParameters : externalContext
				.getRequestParameterValuesMap().entrySet()) {
			String key = (String) requestParameters.getKey();
			if (key.contains("NAO_VALIDAR")) {
				externalContext.getRequestMap().put("VALIDATE", Boolean.FALSE);
				return false;
			}
		}
		externalContext.getRequestMap().put("VALIDATE", Boolean.TRUE);
		return true;
	}

	public static boolean validacaoCNPJ(String cnpj) {
		return validarCNPJ(ConverterUtil.removerFormatacaoCNPJ(cnpj));
	}

	public static boolean validacaoCPF(String cpf) {
		return validacaoCPF(cpf, false);
	}

	private static List<String> getCpfsInvalidos() {
		if (CPFS_INVALIDOS == null) {
			CPFS_INVALIDOS = Arrays.asList(new String[] { "00000000000",
					"11111111111", "22222222222", "33333333333", "44444444444",
					"55555555555", "66666666666", "77777777777", "88888888888",
					"99999999999" });
		}
		return CPFS_INVALIDOS;
	}

	public static boolean validacaoCPF(String cpf,
			boolean desconsiderarCpfIguais) {
		String cpfValidado = cpf;
		if (cpf.length() == 14) {
			cpfValidado = ConverterUtil.removerFormatacaoCPF(cpf);
		}
		if (cpfValidado.length() != 11) {
			return false;
		}
		if ((desconsiderarCpfIguais)
				&& (getCpfsInvalidos().contains(cpfValidado))) {
			return false;
		}
		String numDig = cpfValidado.substring(0, 9);
		return calculoDigitoVerificador(numDig).equals(
				cpfValidado.substring(9, 11));
	}

	private static String calculoDigitoVerificador(String numero) {
		int soma = 0;
		int peso = 10;
		for (int i = 0; i < numero.length(); i++) {
			soma += Integer.parseInt(numero.substring(i, i + 1)) * peso--;
		}

		Integer primDig;
		if (((soma % 11 == 0 ? 1 : 0) | (soma % 11 == 1 ? 1 : 0)) != 0) {
			primDig = new Integer(0);
		} else {
			primDig = new Integer(11 - soma % 11);
		}
		soma = 0;
		peso = 11;
		for (int i = 0; i < numero.length(); i++) {
			soma += Integer.parseInt(numero.substring(i, i + 1)) * peso--;
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

	public static boolean isStringNaoNulaNaoVazia(String string) {
		return StringUtils.isNotBlank(string);
	}

	public static boolean validacaoEmail(String email) {
		Boolean valido = Boolean.valueOf(false);
		if (isStringNaoNulaNaoVazia(email)) {
			String padraoMaster = "[A-Za-z0-9\\._-]+@[A-Za-z]+\\.[A-Za-z]+(\\.[A-Za-z]+)*";
			Pattern p = Pattern.compile(padraoMaster);
			Matcher m = p.matcher(email);
			valido = Boolean.valueOf(m.matches());
		}
		return valido.booleanValue();
	}

	private static boolean validarCNPJ(String cnpjStr) {
		int soma = 0;
		int digito = -1;
		if (cnpjStr.length() != 14) {
			return false;
		}
		String cnpj_calc = cnpjStr.substring(0, 12);

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

}
