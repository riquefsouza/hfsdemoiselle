package br.hfs.util.chart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChartServlet extends HttpServlet {
	private static final long serialVersionUID = 770489670094293924L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String sArquivo = req.getParameter("arquivo");
		ChartUtil chartUtil = new ChartUtil();
		try {
			chartUtil.gerarGrafico(res, sArquivo);
		} catch (ChartException e) {
			e.printStackTrace();
		}
	}

}
