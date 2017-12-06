package br.hfs.util.jdbc;

import java.sql.SQLException;

public class JdbcExemplo {

	public static void main(String[] args) {
		JdbcUtil ju = new JdbcUtil();

		try {
			String driver = "org.sqlite.JDBC";
			String url = "jdbc:sqlite:C:/Demoiselle/workspace/hfs-demoiselle/sqlite/estrutura.sqlite";
			String login = "";
			String senha = "";

			EstruturaDAO.setConexao(ju.Conectar(driver, url, login, senha));
			
			/*
			String arquivo = "C:/Demoiselle/workspace/hfs-demoiselle/sqlite/estrutura_sqlite.txt";
			try {
				EstruturaDAO.getInstancia().inserirDados(arquivo);
			} catch (IOException e) {
				System.out.println("IOException: " + e.getMessage());
			}
			*/
			
			listar();

		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			try {
				ju.Desconectar(EstruturaDAO.getConexao());
			} catch (SQLException e) {
				System.out.println("SQLException desconectar: " + e.getMessage());
			}
		}

	}

	private static void listar() throws SQLException {
		EstruturaDAO.getInstancia().carregarEstruturaPai();

		for (Estrutura item : EstruturaDAO.getInstancia()
				.getListaEstruturaPai()) {
			System.out.println(item.getCategoria() + " - "
					+ item.getDescricao());
		}
	}

}
