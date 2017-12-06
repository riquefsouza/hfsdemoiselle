package br.hfs.util.report;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.hfs.business.UsuarioBC;
import br.hfs.domain.Usuario;
import br.hfs.util.report.ReportUtil.OpcaoGeracao;

public class ReportExemploServlet extends ReportServlet {
	private static final long serialVersionUID = 1L;
	
	private String caminho;

	@Inject
	UsuarioBC usuarioBC;

	@Inject
	ReportUtil reportUtil;

	public void init(ServletConfig cfg) {
		super.init(cfg);
		this.caminho = cfg.getServletContext().getRealPath(File.separator);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		super.doGet(req, res);

		try {
			List<Usuario> listaResultado = usuarioBC.findAll();
			UsuarioJRDS fonteDados = new UsuarioJRDS(listaResultado);

			if (getOpcao() == OpcaoGeracao.PADRAO) {
				reportUtil.relatorioPDF(res, getDiretorioRelatorio(),
						parametros(), fonteDados);
			} else if (getOpcao() == OpcaoGeracao.ARQUIVO) {
				reportUtil.relatorioPorFormato(res, getDiretorioRelatorio(),
						parametros(), fonteDados, getFormatoArquivo(),
						getDiretorioArquivo(), isCompactado(), true);
			}
		} catch (ReportException e) {
			throw new ServletException(e);
		}
	}

	public Map<String, Object> parametros() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("IMAGEM", caminho + "/resources/images/logo.png");
		params.put("PARAMETRO1", "teste de parametro");
		return params;
	}

}
