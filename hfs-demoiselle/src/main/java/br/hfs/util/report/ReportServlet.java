package br.hfs.util.report;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.hfs.util.report.ReportUtil.OpcaoGeracao;

public abstract class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = -864178149348017174L;

	private String caminhoJasper;
	private String diretorioArquivo;
	private String diretorioRelatorio;
	private OpcaoGeracao opcao;
	private String nome;
	private ReportUtil.FormatoArquivo formatoArquivo;
	private boolean compactado;

	public void init(ServletConfig cfg) {
		this.caminhoJasper = cfg.getServletContext().getRealPath("WEB-INF/classes/reports");
		this.diretorioArquivo = cfg.getServletContext().getRealPath("WEB-INF");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String sopcao = req.getParameter("opcao");
		this.nome = req.getParameter("nome");
		String formato = req.getParameter("formato");

		this.compactado = (new Boolean(req.getParameter("compactado")))
				.booleanValue();
		this.diretorioRelatorio = getCaminhoJasper() + "/" + getNome()
				+ ".jasper";

		if (sopcao.equals("PADRAO"))
			this.opcao = OpcaoGeracao.PADRAO;
		else if (sopcao.equals("ARQUIVO"))
			this.opcao = OpcaoGeracao.ARQUIVO;
		
		if (formato.equals("PDF"))
			this.formatoArquivo = ReportUtil.FormatoArquivo.PDF;
		else if (formato.equals("HTML"))
			this.formatoArquivo = ReportUtil.FormatoArquivo.HTML;
		else if (formato.equals("XLS"))
			this.formatoArquivo = ReportUtil.FormatoArquivo.XLS;
		else if (formato.equals("RTF"))
			this.formatoArquivo = ReportUtil.FormatoArquivo.RTF;
		else
			this.formatoArquivo = ReportUtil.FormatoArquivo.PDF;
	}

	public String getCaminhoJasper() {
		return caminhoJasper;
	}

	public String getDiretorioArquivo() {
		return diretorioArquivo;
	}

	public OpcaoGeracao getOpcao() {
		return opcao;
	}

	public String getNome() {
		return nome;
	}

	public String getDiretorioRelatorio() {
		return diretorioRelatorio;
	}

	public boolean isCompactado() {
		return compactado;
	}

	public ReportUtil.FormatoArquivo getFormatoArquivo() {
		return formatoArquivo;
	}
}
