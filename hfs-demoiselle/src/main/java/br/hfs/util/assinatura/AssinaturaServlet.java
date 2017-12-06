package br.hfs.util.assinatura;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

public class AssinaturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("AssinaturaServlet");

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
	
		ServletContext sc = this.getServletContext();	
		//String fileName = req.getParameter("arquivo");

		String query = req.getQueryString();
		
		if (!query.isEmpty()) {		
			String[] params = query.split("=");
			String fileName = params[1];
			
			String path = sc.getRealPath(File.separator) + "temp/" + fileName;
			File fArq = new File(path);
	
			if (fArq.exists()) {
				fArq.delete();
			}
			
			try {
				FileUtils.copyInputStreamToFile(req.getInputStream(), fArq);
			} catch (IOException e) {
				log.severe(e.getMessage());
			}
		} else {
			log.severe("QueryString vazia!");
		}
		
		/*
		try {
			ServletContext sc = this.getServletContext();	
			String fileName = req.getParameter("arquivo");
			String path = sc.getRealPath(File.separator) + "temp/" + fileName;
			File fArq = new File(path);

			if (fArq.exists()) {
				fArq.delete();
			}

			FileOutputStream toFile = new FileOutputStream(fArq);
			DataInputStream fromClient = new DataInputStream(
					req.getInputStream());

			byte[] buff = new byte[1024];
			int cnt = 0;
			while ((cnt = fromClient.read(buff)) > -1) {
				toFile.write(buff, 0, cnt);
			}
			toFile.flush();
			toFile.close();
			fromClient.close();
		} catch (FileNotFoundException e) {
			log.severe(e.getMessage());
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
		 */
	}

}
