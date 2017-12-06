package br.hfs.util.jdbc;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public final class EstruturaDAO {
	private static EstruturaDAO instancia;

	private static final String SQL_CONTA = "select count(*) from consulta";
	private static final String SQL_PAI = "select codigo_organizacao, codigo, codigo_pai, descricao,sigla, categoria from consulta_pai order by 1,2,3";
	private static final String SQL = "select codigo_organizacao, codigo, codigo_pai, descricao,sigla, categoria from consulta order by 1,2,3";

	private List<Estrutura> listaEstrutura;
	private List<Estrutura> listaEstruturaPai;

	private JdbcUtil jdbcUtil;
	
	private static Connection conexao;

	private EstruturaDAO() {
		listaEstrutura = new ArrayList<Estrutura>();
		listaEstruturaPai = new ArrayList<Estrutura>();
		jdbcUtil = new JdbcUtil();
	}

	public static EstruturaDAO getInstancia() {
		if (instancia == null) {
			instancia = new EstruturaDAO();
		}
		return instancia;
	}
	
	public void limparListas(){		
		listaEstruturaPai.clear();
		listaEstrutura.clear();
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
	
	public void carregarEstruturaPai() throws SQLException {
		EstruturaPK estruturaPK;
		Estrutura estrutura;

		try {
			ResultSet res = jdbcUtil.executarSQL(conexao, SQL_PAI);
			while (res.next()) {
				estrutura = new Estrutura();
				estruturaPK = new EstruturaPK();
				estruturaPK.setCodigoOrganizacao(res.getInt(1));
				estruturaPK.setCodigo(res.getInt(2));
				estrutura.setId(estruturaPK);
				// estrutura.setCodigoPai(res.getInt(3));
				estrutura.setDescricao(res.getString(4));
				estrutura.setSigla(res.getString(5));
				estrutura.setCategoria(res.getString(6));

				listaEstruturaPai.add(estrutura);
			}
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}

	}

	public void carregarEstrutura() throws SQLException {
		EstruturaPK estruturaPK;
		Estrutura estrutura;

		try {
			ResultSet res = jdbcUtil.executarSQL(conexao, SQL);
			while (res.next()) {
				estrutura = new Estrutura();
				estruturaPK = new EstruturaPK();
				estruturaPK.setCodigoOrganizacao(res.getInt(1));
				estruturaPK.setCodigo(res.getInt(2));
				estrutura.setId(estruturaPK);

				if (res.getObject(3) == null && res.wasNull()) {
					estrutura.setCodigoPai(-1);
				} else {
					estrutura.setCodigoPai(res.getInt(3));
				}
				estrutura.setDescricao(res.getString(4));
				estrutura.setSigla(res.getString(5));
				estrutura.setCategoria(res.getString(6));

				listaEstrutura.add(estrutura);
			}
		} finally {
			jdbcUtil.fecharSqlPreparado();
		}
	}

	public List<Estrutura> itensFilhos(List<Estrutura> lista_pai,
			int codigo_organizacao, int codigo_pai) {
		List<Estrutura> lista = new ArrayList<Estrutura>();
		for (Estrutura item : lista_pai) {
			if (item.getId().getCodigoOrganizacao() == codigo_organizacao) {
				if (item.getCodigoPai() == codigo_pai) {
					lista.add(item);
				}
			}
		}
		return lista;
	}

	public List<Estrutura> getListaEstrutura() {
		return listaEstrutura;
	}

	public List<Estrutura> getListaEstruturaPai() {
		return listaEstruturaPai;
	}

	public static Connection getConexao() {
		return conexao;
	}

	public static void setConexao(Connection conexao) {
		EstruturaDAO.conexao = conexao;
	}

}
