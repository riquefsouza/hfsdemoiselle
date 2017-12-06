package br.hfs.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.apache.commons.net.ftp.FTPReply;

public final class FtpUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("FtpUtil");

	public static final int TIPO_ARQUIVO = 0;

	public static final int TIPO_DIRETORIO = 1;

	public static final int TIPO_LINKSIMBOLICO = 2;

	public boolean Conectado(FTPClient ftp) {
		return ftp.isConnected();
	}

	public FTPClient ConectaServidorFTP(String servidorFTP, String usuario,
			String senha, String diretorio) throws FtpException {
		FTPClient ftp = new FTPClient();
		try {
			if (!ftp.isConnected()) {
				log.info("Conectando com o Servidor FTP");
				ftp.connect(servidorFTP);
				
				int reply = ftp.getReplyCode();

	            if (!FTPReply.isPositiveCompletion(reply))
	            {
	                ftp.disconnect();
	                throw new FtpException(log, "O servidor FTP recusou a conexão!");
	            }				
				
				ftp.login(usuario, senha);
				ftp.setFileType(FTP.ASCII_FILE_TYPE);
				ftp.enterLocalPassiveMode();
				ftp.changeWorkingDirectory(diretorio);
				return ftp;
			}
		} catch (SocketException e) {
			throw new FtpException(log, "Erro ao conectar no servidor FTP!, "
					+ e.getMessage());
		} catch (IOException e) {
			throw new FtpException(log, "Erro ao conectar no servidor FTP!, "
					+ e.getMessage());
		}
		return null;
	}

	public FTPClient ConectaServidorFTPProxy(String servidorFTP,
			String usuario, String senha, String diretorio, String proxyHost,
			int proxyPort, String proxyUser, String proxyPassword)
			throws FtpException {
		FTPClient ftp = new FTPHTTPClient(proxyHost, proxyPort, proxyUser,
				proxyPassword);
		try {
			if (!ftp.isConnected()) {
				log.info("Conectando com o Servidor FTP");
				ftp.connect(servidorFTP);
				int reply = ftp.getReplyCode();

	            if (!FTPReply.isPositiveCompletion(reply))
	            {
	                ftp.disconnect();
	                throw new FtpException(log, "O servidor FTP recusou a conexão!");
	            }				
				
				ftp.login(usuario, senha);
				ftp.setFileType(FTP.ASCII_FILE_TYPE);
				ftp.enterLocalPassiveMode();
				ftp.changeWorkingDirectory(diretorio);
				return ftp;
			}
		} catch (SocketException e) {
			throw new FtpException(log, "Erro ao conectar no servidor FTP!, "
					+ e.getMessage());
		} catch (IOException e) {
			throw new FtpException(log, "Erro ao conectar no servidor FTP!, "
					+ e.getMessage());
		}
		return null;
	}

	public void DesconectaServidorFTP(FTPClient ftp) throws FtpException {
		try {
			if (ftp.isConnected()) {
				log.info("Desconectando do Servidor FTP");
				ftp.noop();
				ftp.logout();
				ftp.disconnect();
			}
		} catch (IOException e) {
			throw new FtpException(log,
					"Erro ao desconectar do servidor FTP!, " + e.getMessage());
		}
	}

	public FTPFile[] listaArquivos(FTPClient ftp, String sExtensao)
			throws FtpException {
		FTPFile[] arquivos = null;
		if (ftp.isConnected()) {
			try {
				// String[] arq = ftp.listNames("*.pdf");
				arquivos = ftp.listFiles(sExtensao);
			} catch (IOException e) {
				throw new FtpException(log,
						"Erro ao listar arquivos do servidor FTP!, "
								+ e.getMessage());
			}
		}
		return arquivos;
	}

	public List<FtpArquivo> listaArquivosFTP(FTPClient ftp, String caminho,
			boolean somenteDiretorio) throws FtpException {
		List<FtpArquivo> lista = new ArrayList<FtpArquivo>();
		FTPFile[] arquivos = null;
		if (ftp.isConnected()) {
			try {
				arquivos = ftp.listFiles(caminho);
				for (int i = 0; i < arquivos.length; i++) {

					if (somenteDiretorio) {
						if (arquivos[i].getType() == TIPO_DIRETORIO) {
							lista.add(preencheFTPArquivo(arquivos[i]));
						}
					} else {
						lista.add(preencheFTPArquivo(arquivos[i]));
					}
				}
			} catch (IOException e) {
				throw new FtpException(log,
						"Erro ao listar arquivos do servidor FTP!, "
								+ e.getMessage());
			}
		}
		return lista;
	}

	private FtpArquivo preencheFTPArquivo(FTPFile arq) {
		FtpArquivo ftparq = new FtpArquivo();
		ftparq.setNome(arq.getName());
		ftparq.setTamanho(arq.getSize());
		switch (arq.getType()) {
		case TIPO_ARQUIVO:
			ftparq.setTipo(FtpArquivo.Tipo.ARQUIVO);
			break;
		case TIPO_DIRETORIO:
			ftparq.setTipo(FtpArquivo.Tipo.DIRETORIO);
			break;
		case TIPO_LINKSIMBOLICO:
			ftparq.setTipo(FtpArquivo.Tipo.LINKSIMBOLICO);
			break;
		}
		ftparq.setData(arq.getTimestamp().getTime());
		return ftparq;
	}

	public ArrayList<String> listaNomes(FTPClient ftp, String sExtensao)
			throws FtpException {
		ArrayList<String> arquivos = new ArrayList<String>();
		if (ftp.isConnected()) {
			try {
				String[] nomes = ftp.listNames(sExtensao);
				for (int ncont = 0; ncont < nomes.length; ncont++) {
					arquivos.add(nomes[ncont]);
				}
			} catch (IOException e) {
				throw new FtpException(log,
						"Erro ao listar arquivos do servidor FTP!, "
								+ e.getMessage());
			}
		}
		return arquivos;
	}

	public boolean enviarArquivo(FTPClient ftp, String sDir, String sArquivo)
			throws FtpException {
		FileInputStream fis;
		File arq;
		if (ftp.isConnected()) {
			try {
				arq = new File(sDir + sArquivo);
				if (!arq.exists()) {
					throw new FtpException(log, "Arquivo não encontrado ("
							+ sDir + sArquivo + ") no diretorio!");
				}

				fis = new FileInputStream(sDir + sArquivo);

				if (ftp.storeFile(sArquivo, fis)) {
					fis.close();
					return true;
				} else {
					fis.close();
					return false;
				}
			} catch (IOException e) {
				throw new FtpException(log, "Erro ao enviar arquivo (" + sDir
						+ sArquivo + ") para o FTP!, " + e.getMessage());
			}
		}
		return false;
	}

	public boolean receberArquivo(FTPClient ftp, String sDir, String sArquivo)
			throws FtpException {
		FileOutputStream fos;
		if (ftp.isConnected()) {
			try {
				fos = new FileOutputStream(sDir + sArquivo);
				if (ftp.retrieveFile(sArquivo, fos)) {
					fos.close();
					return true;
				} else {
					fos.close();
					return false;
				}
			} catch (IOException e) {
				throw new FtpException(log, "Erro ao receber arquivo ("
						+ sArquivo + ") do FTP!, " + e.getMessage());
			}
		}
		return false;
	}

	public boolean excluirArquivo(FTPClient ftp, String sArquivo)
			throws FtpException {
		if (ftp.isConnected()) {
			try {
				ftp.deleteFile(sArquivo);
				return true;
			} catch (IOException e) {
				throw new FtpException(log, "Erro ao excluir arquivo ("
						+ sArquivo + ") do FTP!, " + e.getMessage());
			}
		}
		return false;
	}
}