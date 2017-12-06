package br.hfs.util.correio;

import java.io.IOException;
import java.sql.SQLException;

import br.hfs.util.jdbc.JdbcUtil;

public class CorreioExemplo {

	
	public static void main(String[] args) throws IOException {
		JdbcUtil ju = new JdbcUtil();

		try {
			String driver = "org.sqlite.JDBC";
			String url = "jdbc:sqlite:C:/Demoiselle/workspace/hfs-demoiselle/correios/correios.sqlite";
			String login = "";
			String senha = "";

			CorreioDAO.setConexao(ju.Conectar(driver, url, login, senha));
			
			String arquivo = "C:/Demoiselle/workspace/hfs-demoiselle/correios/correios.sql";
			try {
				CorreioDAO.getInstancia().inserirDados(arquivo);
			} catch (IOException e) {
				System.out.println("IOException: " + e.getMessage());
			}

		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			try {
				ju.Desconectar(CorreioDAO.getConexao());
			} catch (SQLException e) {
				System.out.println("SQLException desconectar: " + e.getMessage());
			}
		}
		
		
	/*	
		File saida = new File("C:/Demoiselle/workspace/hfs-demoiselle/correios/saida-correios.sql");
		File file = new File("C:/Demoiselle/workspace/hfs-demoiselle/correios/correios.sql");
		List<String> linhas = FileUtils.readLines(file, "UTF-8");
		
		String linha;
		int j = 1;
		for (int i = 0; i < linhas.size(); i++) {
			if (i>=30){
				linha = linhas.get(i).replace("`", "");
				linha = linha.replace("\\'", "''");
				linha = linha.replace("(uf,", "(id,uf,");
				linha = linha.replace("VALUES (", "VALUES (" + j + ",");
				j++;
				 
				System.out.println(i + " - " + linha);
				FileUtils.writeStringToFile(saida, linha + "\n", "UTF-8", true);
			}			
		}

		*/
	}
		
}
