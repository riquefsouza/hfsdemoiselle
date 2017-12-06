package br.hfs.util.mapa;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class MapaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		byte[] conteudo = IOUtils.toByteArray(getClass().getResourceAsStream("mapa.html"));

		res.setContentType("text/html");
		res.setContentLength(conteudo.length);

		// Escreve no browser
		ServletOutputStream ouputStream = res.getOutputStream();
		ouputStream.write(conteudo, 0, conteudo.length);
		ouputStream.flush();
		ouputStream.close();
	}

}
