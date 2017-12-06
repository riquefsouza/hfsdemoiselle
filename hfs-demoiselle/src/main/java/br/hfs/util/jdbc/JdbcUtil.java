package br.hfs.util.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class JdbcUtil {

	private static PreparedStatement pstmt;

	public Connection Conectar(String driver, String url, String login,
			String senha) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection db = DriverManager.getConnection(url, login, senha);
		return db;
	}

	public void Desconectar(Connection db) throws SQLException {
		if (db != null) {
			if (!db.isClosed()) {
				db.close();
			}
		}
	}

	public ResultSet executarSQL(Connection db, String sql) throws SQLException {
		pstmt = db.prepareStatement(sql);
		return pstmt.executeQuery();
	}

	public int executar(Connection db, String sql) throws SQLException {
		if (db != null) {
			pstmt = db.prepareStatement(sql);
			return pstmt.executeUpdate();
		} else {
			return -1;
		}

	}

	public void getPstmt(Connection db, String sql) throws SQLException {
		pstmt = db.prepareStatement(sql);
	}

	public PreparedStatement getPstmt() {
		return pstmt;
	}

	public void fecharSqlPreparado() throws SQLException {
		if (pstmt != null) {
			pstmt.close();
		}
	}

}
