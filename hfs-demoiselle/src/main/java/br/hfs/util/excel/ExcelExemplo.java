package br.hfs.util.excel;

import java.util.List;

import br.hfs.util.correio.Correio;

public class ExcelExemplo {

	public static void main(String[] args) {
		try {
			ExcelUtil eu = new ExcelUtil();
			String arquivo = "C:/Demoiselle/workspace/hfs-demoiselle/correios/correios-simples.xlsx";
			List<Correio> lista = eu.lerArquivoExcelXLSX(arquivo);
			
			for (Correio item : lista) {
				System.out.println(item.toString());
			}
			
		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}

}
