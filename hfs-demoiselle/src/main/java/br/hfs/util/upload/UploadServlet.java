package br.hfs.util.upload;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {
	private static Logger log = Logger.getLogger("UploadServlet");
	private static final long serialVersionUID = -1L;

	private String diretorio;

	public void init(ServletConfig cfg) {
		diretorio = cfg.getServletContext().getRealPath("WEB-INF/upload");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		
		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				List<FileItem> files = upload.parseRequest(req);
				for (FileItem arquivo : files) {
					String nomeArquivo = arquivo.getName();

					try {
						arquivo.write(new File(diretorio, nomeArquivo));
					} catch (Exception e) {
						log.severe("Erro ao salvar o arquivo " + diretorio
								+ nomeArquivo + ", " + e.getMessage());
					}
				}
			} catch (FileUploadException e) {
				log.severe("Erro ao realizar o upload de arquivo, "
						+ e.getMessage());
			}
		}

	}

	public void destroy() {
		// System.out.println("UploadServlet destruído!");
	}
}
