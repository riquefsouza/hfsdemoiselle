package br.hfs.util.sftp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

public class SFtpExemplo {

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();
		
		/*
		try {
			SFtpUtil sftpUtil = new SFtpUtil();

			sftpUtil.setServidorSFTP("127.0.0.1");
			sftpUtil.setUsuario("admin");
			sftpUtil.setSenha("abcd1234");

			String arquivoLocal = "C:/temp/leiame1.txt";
			String arquivoRemoto = "/remoto/leiame1.txt";

			sftpUtil.uploadArquivo(arquivoLocal, arquivoRemoto);
			
			if (sftpUtil.isArquivoRemotoExiste(arquivoRemoto)){
				sftpUtil.downloadArquivo(arquivoLocal, arquivoRemoto);
				//sftpUtil.moverArquivoRemoto(arquivoRemoto, destinoArquivoRemoto);
				sftpUtil.excluirArquivoRemoto(arquivoRemoto);
			}
		} catch (SFtpException e) {
			e.printStackTrace();
		}		
		*/
		
		try {
			SFtpUtil sftpUtil = new SFtpUtil();

			sftpUtil.setServidorSFTP("127.0.0.1");
			sftpUtil.setUsuario("admin");
			sftpUtil.setSenha("abcd1234");

			List<String> arquivos = new ArrayList<String>();
			arquivos.add("DSPACE_XMAP.TXT");
			arquivos.add("DSPACE_XSL.TXT");
			arquivos.add("leiame.txt");
			arquivos.add("leiame1.txt");
			arquivos.add("lista.txt");
			arquivos.add("lista2.txt");
			arquivos.add("lista3.txt");
			
			String diretorioLocal = "c:/temp";
			String diretorioRemoto = "/remoto";

			sftpUtil.uploadArquivos(diretorioRemoto, diretorioLocal, arquivos);
			
			if (sftpUtil.isArquivosRemotosExistem(diretorioRemoto, arquivos)){
				sftpUtil.downloadArquivos(diretorioLocal, diretorioRemoto, arquivos);
				//sftpUtil.moverArquivoRemoto(arquivoRemoto, destinoArquivoRemoto);
				sftpUtil.excluirArquivosRemotos(diretorioRemoto, arquivos);
			}
		} catch (SFtpException e) {
			e.printStackTrace();
		}		
		
	}

}
