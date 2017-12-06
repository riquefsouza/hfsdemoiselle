package br.hfs.util.textsearch;

import java.util.List;

public class TextSearchExemplo {

	public static void main(String[] args) {
		TextSearchUtil tsu = new TextSearchUtil();

		try {
			// tsu.indexarArquivos("c:/lucene-index", "c:/lucene-6.0.0", false);

			List<TextSearch> arquivos = tsu.pesquisarArquivos("c:/lucene-index", "string", 10000);
			
			for (TextSearch item: arquivos) {
				System.out.println(item.getDiretorio() + " - " + item.getArquivo());
			}
			
		} catch (TextSearchException e) {
			e.printStackTrace();
		}
	}

}
