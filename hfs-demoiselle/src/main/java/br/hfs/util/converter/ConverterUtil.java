package br.hfs.util.converter;

import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;

public class ConverterUtil {

	private static NumberFormat currencyFormat;

	public static String removerFormatacaoCEP(String cep) {
		if (cep == null) {
			return null;
		}
		return StringUtils.remove(cep.toUpperCase(), "-");
	}

	// 99999-999
	public static String adicionarFormatacaoCEP(String cep) {
		if (cep == null) {
			return null;
		}
		String cepFormatado = cep;
		if (cep.length() == 8) {
			StringBuilder cepBuilder = new StringBuilder();
			cepBuilder.append(cep.substring(0, 5)).append("-");
			cepBuilder.append(cep.substring(5));
			cepFormatado = cepBuilder.toString();
		}
		return cepFormatado;
	}

	public static String adicionarFormatacaoCNPJ(String cnpj) {
		if (cnpj == null) {
			return null;
		}
		String cnpjFormatado = cnpj;
		if (cnpj.length() == 14) {
			StringBuilder cnpjBuilder = new StringBuilder();
			cnpjBuilder.append(cnpj.substring(0, 2)).append(".");
			cnpjBuilder.append(cnpj.substring(2, 5)).append(".");
			cnpjBuilder.append(cnpj.substring(5, 8)).append("/");
			cnpjBuilder.append(cnpj.substring(8, 12)).append("-");
			cnpjBuilder.append(cnpj.substring(12));
			cnpjFormatado = cnpjBuilder.toString();
		}
		return cnpjFormatado;
	}

	public static String adicionarFormatacaoCPF(String cpf) {
		if (cpf == null) {
			return null;
		}
		String cpfFormatado = cpf;
		if (cpf.length() == 11) {
			StringBuilder cpfBuilder = new StringBuilder();
			cpfBuilder.append(cpf.substring(0, 3)).append(".");
			cpfBuilder.append(cpf.substring(3, 6)).append(".");
			cpfBuilder.append(cpf.substring(6, 9)).append("-");
			cpfBuilder.append(cpf.substring(9));
			cpfFormatado = cpfBuilder.toString();
		}
		return cpfFormatado;
	}

	public static String adicionarFormatacaoTelefone(String telefone) {
		if (telefone == null) {
			return null;
		}
		String telefoneFormatado = telefone;
		if ((telefone.length() == 10) || (telefone.length() == 11)) {
			StringBuilder builder = new StringBuilder();
			builder.append("(").append(telefone.substring(0, 2)).append(")");
			int pontoHifen = telefone.length() - 4;
			builder.append(telefone.substring(2, pontoHifen)).append("-");
			builder.append(telefone.substring(pontoHifen));
			telefoneFormatado = builder.toString();
		}
		return telefoneFormatado;
	}

	public static String removerFormatacaoCNPJ(String cnpj) {
		String cnpjSemMascara = "";
		if (cnpj != null) {
			cnpjSemMascara = StringUtils.remove(cnpj.toUpperCase(), "_");
			cnpjSemMascara = cnpjSemMascara.replaceAll("\\.|\\-|/", "");
		}
		return cnpjSemMascara;
	}

	public static String removerFormatacaoCPF(String cpf) {
		if (cpf == null) {
			return null;
		}
		String cpfFormatado = StringUtils.remove(cpf.toUpperCase(), "_");
		cpfFormatado = StringUtils.remove(cpfFormatado.toUpperCase(), '.');
		cpfFormatado = StringUtils.remove(cpfFormatado, '-');
		return cpfFormatado;
	}

	public static String removerFormatacaoDataVazia(String stringData) {
		String stringSemFormatacao = "";
		if ((stringData != null) && (!"__/__/____".equals(stringData))) {
			stringSemFormatacao = StringUtils.remove(stringData, "_");
		}
		return stringSemFormatacao;
	}

	public static String removerFormatacaoTelefone(String telefone) {
		String telefoneSemMascara = "";
		if (telefone != null) {
			telefoneSemMascara = StringUtils.remove(telefone, "_");
			telefoneSemMascara = telefoneSemMascara.replaceAll("\\D|\\s", "");
		}
		return telefoneSemMascara;
	}

	public static String retirarFormatacaoNumeroDecimal(String valor) {
		String retorno = null;
		if ((valor != null) && (!valor.isEmpty())) {
			retorno = valor.replaceAll("\\.", "").replaceAll(",", ".");
		}
		return retorno;
	}

	public static Double converteMascaraValorParaDouble(String mascaraValor) {
		Double valor = null;
		String valorStr = retirarFormatacaoNumeroDecimal(mascaraValor);
		if (valorStr != null) {
			try {
				valor = Double.valueOf(Double.parseDouble(valorStr));
			} catch (Exception localException) {
			}
		}
		return valor;
	}

	public static NumberFormat getCurrencyFormat() {
		if (currencyFormat == null) {
			currencyFormat = NumberFormat.getInstance();
			currencyFormat.setGroupingUsed(true);
			currencyFormat.setMinimumFractionDigits(2);
			currencyFormat.setMaximumFractionDigits(2);
		}
		return currencyFormat;
	}

	public static String formatarDoubleParaMascaraValor(Double valor) {
		String valorMascara = "";
		if (valor != null) {
			valorMascara = getCurrencyFormat().format(valor);
		}
		return valorMascara;
	}

	public static String itrim(String source) {
		return source != null ? source.trim().replaceAll("\\s{2,}", " ") : null;
	}

}
