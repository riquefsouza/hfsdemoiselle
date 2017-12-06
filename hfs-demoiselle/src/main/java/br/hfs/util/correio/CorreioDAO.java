package br.hfs.util.correio;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.hfs.util.jdbc.JdbcUtil;

public class CorreioDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static CorreioDAO instancia;

	private static final String SQL = "select id,uf,estado,ufcep1,ufcep2,cidade,logradouro,bairro,cep,tp_logradouro from correios";
	private static final String SQL_ID = SQL + " where id=?";
	private static final String SQL_CONTA = "select count(*) from correios";
	
	private static final String INSERT = "insert into correios(id,uf,estado,ufcep1,ufcep2,cidade,logradouro,bairro,cep,tp_logradouro) values (?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE = "update correios set uf=?,estado=?,ufcep1=?,ufcep2=?,cidade=?,logradouro=?,bairro=?,cep=?,tp_logradouro=? where id=?";
	private static final String DELETE = "delete from correios where id=?";

	private static final String SQL_ESTADO = "select distinct estado from correios order by 1";
	private static final String SQL_CIDADES_POR_ESTADO = "select distinct cidade from correios where estado=? order by 1";
	private static final String SQL_TIPO_LOGRADOURO = "select distinct tp_logradouro from correios";
	
	private static final String SQL_PAGINACAO = "select * from (select tabela.*, id linha from (select * from correios order by id) tabela where id < ((? * ?) + 1 )) where linha >= (((?-1) * ?) + 1)";
	private static final String SQL_INTERVALO = "select * from (select tabela.*, id linha from (select * from correios order by id) tabela where id <= ?) where linha >= ?";	
	
	private JdbcUtil jdbcUtil;

	private static Connection conexao;
	
	private CorreioDAO() {
		jdbcUtil = new JdbcUtil();
	}

	public static CorreioDAO getInstancia() {
		if (instancia == null) {
			instancia = new CorreioDAO();
		}
		return instancia;
	}

	public static Connection getConexao() {
		return conexao;
	}

	public static void setConexao(Connection conexao) {
		CorreioDAO.conexao = conexao;
	}
	
	public void inserirDados(String arquivo) throws IOException, SQLException {
		File file = new File(arquivo);
		List<String> linhas = FileUtils.readLines(file, "UTF-8");

		for (int i = 0; i < linhas.size(); i++) {
			System.out.println("linha: " + i + " - " + linhas.get(i));

			jdbcUtil.executar(conexao, linhas.get(i));
		}
		jdbcUtil.fecharSqlPreparado();
	}

	public int salvar(String sql) throws SQLException {
		int res = -1;
		try {
			res = jdbcUtil.executar(conexao, sql);
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return res;
	}

	public List<Correio> consultar(String sql) throws SQLException {
		List<Correio> lista = new ArrayList<Correio>();
		try {
			Correio co;
			ResultSet res = jdbcUtil.executarSQL(conexao, sql);
			while (res.next()) {
				co = setCorreios(res);
				lista.add(co);
			}
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return lista;
	}

	private Correio setCorreios(ResultSet res) throws SQLException {
		Correio co;
		co = new Correio();
		co.setId(res.getLong(1));
		co.setUf(res.getString(2));
		co.setEstado(res.getString(3));
		co.setUfCep1(res.getString(4));
		co.setUfCep2(res.getString(5));
		co.setCidade(res.getString(6));
		co.setLogradouro(res.getString(7));
		co.setBairro(res.getString(8));
		co.setCep(res.getString(9));
		co.setTipoLogradouro(res.getString(10));
		return co;
	}

	public int contaTotal() throws SQLException {
		int total = -1;
		try {
			ResultSet res = jdbcUtil.executarSQL(conexao, SQL_CONTA);
			if (res.next()) {
				total = res.getInt(1);
			}
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return total;
	}

	public List<Correio> listarPaginada(int numeroPagina, int tamanhoPagina) throws SQLException {
		List<Correio> lista = new ArrayList<Correio>();
		Correio co;
		try {
			jdbcUtil.getPstmt(conexao, SQL_PAGINACAO);
			jdbcUtil.getPstmt().setInt(1, numeroPagina);
			jdbcUtil.getPstmt().setInt(2, tamanhoPagina);
			jdbcUtil.getPstmt().setInt(3, numeroPagina);
			jdbcUtil.getPstmt().setInt(4, tamanhoPagina);
			ResultSet res = jdbcUtil.getPstmt().executeQuery();
			while (res.next()) {
				co = setCorreios(res);
				lista.add(co);
			}
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return lista;		
	}
	
	public List<Correio> listarPorIntervalo(int intervaloInicial, int intervaloFinal) throws SQLException {
		List<Correio> lista = new ArrayList<Correio>();
		Correio co;
		try {
			jdbcUtil.getPstmt(conexao, SQL_INTERVALO);
			jdbcUtil.getPstmt().setInt(1, intervaloFinal);
			jdbcUtil.getPstmt().setInt(2, intervaloInicial);
			ResultSet res = jdbcUtil.getPstmt().executeQuery();
			while (res.next()) {
				co = setCorreios(res);
				lista.add(co);
			}
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return lista;		
	}
	
	public List<Correio> findAll() throws SQLException {
		return consultar(SQL);
	}

	public Correio load(Integer id) throws SQLException {
		Correio co = new Correio();
		try {
			jdbcUtil.getPstmt(conexao, SQL_ID);
			jdbcUtil.getPstmt().setInt(1, id);
			ResultSet res = jdbcUtil.getPstmt().executeQuery();
			if (res.next()) {
				co = setCorreios(res);
			}
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return co;
	}

	public int insert(Correio co) throws SQLException {
		int res = -1;
		try {
			jdbcUtil.getPstmt(conexao, INSERT);
			jdbcUtil.getPstmt().setLong(1, co.getId());
			jdbcUtil.getPstmt().setString(2, co.getUf());
			jdbcUtil.getPstmt().setString(3, co.getEstado());
			jdbcUtil.getPstmt().setString(4, co.getUfCep1());
			jdbcUtil.getPstmt().setString(5, co.getUfCep2());
			jdbcUtil.getPstmt().setString(6, co.getCidade());
			jdbcUtil.getPstmt().setString(7, co.getLogradouro());
			jdbcUtil.getPstmt().setString(8, co.getBairro());
			jdbcUtil.getPstmt().setString(9, co.getCep());
			jdbcUtil.getPstmt().setString(10, co.getTipoLogradouro());
			res = jdbcUtil.getPstmt().executeUpdate();
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return res;
	}

	public int update(Correio co) throws SQLException {
		int res = -1;
		try {
			jdbcUtil.getPstmt(conexao, UPDATE);
			jdbcUtil.getPstmt().setString(1, co.getUf());
			jdbcUtil.getPstmt().setString(2, co.getEstado());
			jdbcUtil.getPstmt().setString(3, co.getUfCep1());
			jdbcUtil.getPstmt().setString(4, co.getUfCep2());
			jdbcUtil.getPstmt().setString(5, co.getCidade());
			jdbcUtil.getPstmt().setString(6, co.getLogradouro());
			jdbcUtil.getPstmt().setString(7, co.getBairro());
			jdbcUtil.getPstmt().setString(8, co.getCep());
			jdbcUtil.getPstmt().setString(9, co.getTipoLogradouro());
			jdbcUtil.getPstmt().setLong(10, co.getId());
			res = jdbcUtil.getPstmt().executeUpdate();
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return res;
	}

	public int delete(Integer id) throws SQLException {
		int res = -1;
		try {
			jdbcUtil.getPstmt(conexao, DELETE);
			jdbcUtil.getPstmt().setInt(1, id);
			res = jdbcUtil.getPstmt().executeUpdate();
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return res;
	}

	public List<String> listar(String sql) throws SQLException {
		List<String> lista = new ArrayList<String>();
		try {
			ResultSet res = jdbcUtil.executarSQL(conexao, sql);
			while (res.next()) {
				lista.add(res.getString(1));
			}
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return lista;
	}
	
	public List<String> listarEstados() throws SQLException {
		return listar(SQL_ESTADO);
	}
	
	public List<String> listarTiposLogradouro() throws SQLException {
		return listar(SQL_TIPO_LOGRADOURO);
	}
	
	public List<String> pesquisarCidadesPorEstado(String estado) throws SQLException {
		List<String> lista = new ArrayList<String>();
		try {
			jdbcUtil.getPstmt(conexao, SQL_CIDADES_POR_ESTADO);
			jdbcUtil.getPstmt().setString(1, estado);
			ResultSet res = jdbcUtil.getPstmt().executeQuery();
			while (res.next()) {
				lista.add(res.getString(1));
			}
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
		return lista;
	}

}
