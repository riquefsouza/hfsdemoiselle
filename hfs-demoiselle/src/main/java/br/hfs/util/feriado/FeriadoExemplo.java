package br.hfs.util.feriado;

import java.util.ArrayList;

public class FeriadoExemplo {

	private static ArrayList<Feriado> listaFeriados;

	public static void main(String[] args) throws Exception {
		FeriadoUtil feriado = new FeriadoUtil();

		listaFeriados = feriado.gerarFeriadosRecife(2015, 2016);

		for (Feriado item : listaFeriados) {
			System.out.println(item);
		}
	}

}
