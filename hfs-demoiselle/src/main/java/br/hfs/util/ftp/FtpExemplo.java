package br.hfs.util.ftp;

import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

public class FtpExemplo {

	public static void main(String[] args) throws IOException {
		try {
			FtpUtil ftpUtil = new FtpUtil();

			FTPClient cliente = ftpUtil.ConectaServidorFTP("10.1.107.48",
			 "admin", "abcd1234", "remoto");
			/*
			FTPClient cliente = ftpUtil.ConectaServidorFTPProxy(
					"ftp.unicamp.br", "anonymous", "riquefsouza@gmail.com",
					"pub", "bravo.trtrio.gov.br", 8080, "henrique.souza", "");
			*/
			List<FtpArquivo> lista = ftpUtil.listaArquivosFTP(cliente, ".",
					false);

			for (FtpArquivo arq : lista) {
				System.out.println(arq.getNome());
			}

			ftpUtil.DesconectaServidorFTP(cliente);
		} catch (FtpException e) {
			e.printStackTrace();
		}
	}

}
