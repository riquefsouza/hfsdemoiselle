package br.hfs.util.assinatura;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

public class AssinaturaServletUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("AssinaturaServletUtil");
	
	public void enviarArquivoToServlet(String urlBase, byte[] arquivo, String nomeArquivo) 
			throws AssinaturaDigitalException {

		 try {
			 String toservlet = urlBase + "/assinatura?arquivo=" + nomeArquivo;
			 
			 URLConnection conn = new URL(toservlet).openConnection();
			 conn.setDoOutput(true);
			 conn.setDoInput(true);
			 conn.setUseCaches(false);
			 conn.setDefaultUseCaches(false);

			 //OutputStream outs = conn.getOutputStream();
			 DataOutputStream out = new DataOutputStream(conn.getOutputStream());

			 out.write(arquivo);
			 out.flush();   
			 out.close();
			 
			DataInputStream inputFromClient = new DataInputStream(conn.getInputStream());
			// pegar o que quer vindo do servlet
			inputFromClient.close();			 
		} catch (MalformedURLException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
		
		
		
		/*
		
		try {
			File file = new File(arquivo);

			FileInputStream in = new FileInputStream(file);
			byte[] buf = new byte[in.available()];
			int bytesread = 0;

			String toservlet = urlBase + "/assinatura?arquivo=" + file.getName();

			URL servleturl = new URL(toservlet);
			URLConnection servletconnection = servleturl.openConnection();
			
			servletconnection.setDoInput(true);
			servletconnection.setDoOutput(true);
			servletconnection.setUseCaches(false);
			servletconnection.setDefaultUseCaches(false);

			DataOutputStream out = new DataOutputStream(
					servletconnection.getOutputStream());

			while ((bytesread = in.read(buf)) > -1) {
				out.write(buf, 0, bytesread);
			}

			out.flush();
			out.close();
			in.close();

			DataInputStream inputFromClient = new DataInputStream(
					servletconnection.getInputStream());
			// pegar o que quer vindo do servlet

			inputFromClient.close();
		} catch (FileNotFoundException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (MalformedURLException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		} catch (IOException e) {
			throw new AssinaturaDigitalException(log, e.getMessage());
		}
		
		*/
	}
	
	public static void main(String[] args) {
		try {
			AssinaturaServletUtil asu = new AssinaturaServletUtil();
			
			byte[] arq = FileUtils.readFileToByteArray(new File("C:\\VMs\\vazio1.pdf"));
			
			asu.enviarArquivoToServlet("http://localhost:8080/hfs-demoiselle", arq, "vazio1.pdf");
			//asu.enviarArquivoToServlet("http://localhost:8080/hfs-demoiselle", "c:/temp/assinado.pdf");
		} catch (AssinaturaDigitalException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		try {
			String urlBase = "http://localhost:8080/hfs-demoiselle";
			String arq = "c:/VMs/";
			String item = "assinado.pdf";
			
			URL url = new URL(urlBase + "/temp/" + item);
			arq += item; 
			FileUtils.copyURLToFile(url, new File(arq));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
}
