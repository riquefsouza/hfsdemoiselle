package br.hfs.util.scanner;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.hfs.util.StringUtil;

public class ScannerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String diretorioScanner;
	
	public void init(ServletConfig cfg) {
		this.diretorioScanner = cfg.getServletContext().getRealPath("WEB-INF");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		int tamanho = req.getContentLength();
		byte[] dados = new byte[tamanho];

		ServletInputStream sin = req.getInputStream();
		dados = IOUtils.toByteArray(sin);
		
		byte[] conteudo = Base64.decodeBase64(dados);
		System.out.println("Tamanho Dados ScannerServlet: "+conteudo.length);
		//byte[] conteudo = RotinasZIP.getInstancia().descomprimirByteArray(dados);

		String arq = StringUtil.getArquivoRandom(diretorioScanner, "jpg");
		FileUtils.writeByteArrayToFile(new File(arq), conteudo, true);
	}

}
