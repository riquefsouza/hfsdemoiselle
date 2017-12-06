package br.hfs.util;

import org.apache.commons.lang3.RandomStringUtils;

public final class StringUtil {

	public static String cap(String valor) {
		return "get" + valor.toUpperCase().charAt(0) + valor.substring(1);
	}

	public static String removeDir(String arquivo) {
		if (arquivo.indexOf('\\') >= 0) {
			return arquivo.substring(arquivo.lastIndexOf('\\') + 1);
		} else {
			return arquivo;
		}
	}

	public static String getArquivoRandom(String diretorio, String extensao) {
		String arquivo = diretorio + "/"
				+ RandomStringUtils.randomAlphanumeric(32).toUpperCase() + "."
				+ extensao;
		return arquivo;
	}

	public static String getArquivoRandom(String extensao) {
		String arquivo = RandomStringUtils.randomAlphanumeric(32).toUpperCase()
				+ "." + extensao;
		return arquivo;
	}

	public static String getArquivoRandom() {
		return RandomStringUtils.randomAlphanumeric(32).toUpperCase();
	}
	
}
