package br.hfs.util.textsearch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.demo.IndexFiles;
import org.apache.lucene.demo.SearchFiles;

public class TextSearchUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("TextSearchUtil");

	public void indexarArquivos(String diretorioIndexado, String diretorio,
			boolean atualizar) throws TextSearchException {
		String[] paramsIndexacao;

		try {
			if (atualizar) {
				paramsIndexacao = new String[] { "-index", diretorioIndexado,
						"-docs", diretorio };
			} else {
				paramsIndexacao = new String[] { "-index", diretorioIndexado,
						"-docs", diretorio, "-update" };
			}

			IndexFiles.main(paramsIndexacao);

		} catch (Exception e) {
			throw new TextSearchException(log, e.getMessage());
		}
	}

	public List<TextSearch> pesquisarArquivos(String diretorioIndexado,
			String consulta, int maxResultados) throws TextSearchException {
		List<TextSearch> arquivos = new ArrayList<TextSearch>();
		List<String> lista;
		TextSearch tsArquivo;
		String diretorio, arquivo;

		// Iniciar Captura
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		System.setOut(new PrintStream(buffer));

		try {
			String[] paramsPesquisa = new String[] { "-index",
					diretorioIndexado, "-query", consulta, "-paging",
					Integer.toString(maxResultados) };

			SearchFiles.main(paramsPesquisa);

			// Parar captura
			System.setOut(new PrintStream(new FileOutputStream(
					FileDescriptor.out)));

			String conteudo = buffer.toString();
			buffer.reset();

			if (!conteudo.isEmpty()) {
				lista = Arrays.asList(conteudo.split(System.lineSeparator()));

				if (lista.size() > 0) {
					for (String item : lista) {
						if (!item.startsWith("Searching for:")
								&& !(item.endsWith("total matching documents"))) {
							conteudo = item.substring(item.indexOf('.') + 2);

							diretorio = conteudo.substring(0,
									conteudo.lastIndexOf(File.separatorChar));
							arquivo = conteudo.substring(conteudo
									.lastIndexOf(File.separatorChar) + 1);
							tsArquivo = new TextSearch(diretorio, arquivo);

							arquivos.add(tsArquivo);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new TextSearchException(log, e.getMessage());
		}

		return arquivos;
	}

}
