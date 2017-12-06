package br.hfs.util.sftp;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

public final class SFtpUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("SFtpUtil");

	private String servidorSFTP;
	private String usuario;
	private String senha;

	private String criarStringConexao(String servidorSFTP, String usuario,
			String senha, String arquivoRemoto) {
		return "sftp://" + usuario + ":" + senha + "@" + servidorSFTP + "/"
				+ arquivoRemoto;
	}

	private FileSystemOptions criarOpcoesPadrao() throws FileSystemException {
		FileSystemOptions opts = new FileSystemOptions();
		SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(
				opts, "no");
		SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false);
		SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

		return opts;
	}

	public boolean isArquivoRemotoExiste(String arquivoRemoto)
			throws SFtpException {
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			manager.init();

			FileObject remoteFile = manager.resolveFile(
					criarStringConexao(servidorSFTP, usuario, senha,
							arquivoRemoto), criarOpcoesPadrao());

			return remoteFile.exists();
		} catch (FileSystemException e) {
			throw new SFtpException(log, e.getMessage());
		} finally {
			manager.close();
		}
	}

	public boolean moverArquivoRemoto(String arquivoRemoto,
			String destinoArquivoRemoto) throws SFtpException {
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			manager.init();

			FileObject remoteFile = manager.resolveFile(
					criarStringConexao(servidorSFTP, usuario, senha,
							arquivoRemoto), criarOpcoesPadrao());

			FileObject remoteDestFile = manager.resolveFile(
					criarStringConexao(servidorSFTP, usuario, senha,
							destinoArquivoRemoto), criarOpcoesPadrao());

			if (remoteFile.exists()) {
				remoteFile.moveTo(remoteDestFile);
				return true;
			} else {
				return false;
			}
		} catch (FileSystemException e) {
			throw new SFtpException(log, e.getMessage());
		} finally {
			manager.close();
		}
	}

	public void downloadArquivo(String arquivoLocal, String arquivoRemoto)
			throws SFtpException {
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			manager.init();

			FileObject localFile = manager.resolveFile(arquivoLocal);

			FileObject remoteFile = manager.resolveFile(
					criarStringConexao(servidorSFTP, usuario, senha,
							arquivoRemoto), criarOpcoesPadrao());

			localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);

		} catch (FileSystemException e) {
			throw new SFtpException(log, e.getMessage());
		} finally {
			manager.close();
		}
	}

	public void uploadArquivo(String arquivoLocal, String arquivoRemoto)
			throws SFtpException {

		File file = new File(arquivoLocal);
		if (!file.exists()) {
			throw new SFtpException(log, "Arquivo local não encontrado!");
		}

		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			manager.init();

			FileObject localFile = manager.resolveFile(file.getAbsolutePath());

			FileObject remoteFile = manager.resolveFile(
					criarStringConexao(servidorSFTP, usuario, senha,
							arquivoRemoto), criarOpcoesPadrao());

			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);

		} catch (FileSystemException e) {
			throw new SFtpException(log, e.getMessage());
		} finally {
			manager.close();
		}
	}

	public void excluirArquivoRemoto(String arquivoRemoto) throws SFtpException {
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			manager.init();

			FileObject remoteFile = manager.resolveFile(
					criarStringConexao(servidorSFTP, usuario, senha,
							arquivoRemoto), criarOpcoesPadrao());

			if (remoteFile.exists()) {
				remoteFile.delete();
			}
		} catch (FileSystemException e) {
			throw new SFtpException(log, e.getMessage());
		} finally {
			manager.close();
		}
	}

	public boolean isArquivosRemotosExistem(String diretorioRemoto,
			List<String> arquivosRemotos) throws SFtpException {
		boolean bExiste = true;
		FileObject remoteFile;
		String itemRemoto;
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			manager.init();

			for (String arquivoRemoto : arquivosRemotos) {
				itemRemoto = diretorioRemoto + "/" + arquivoRemoto;

				remoteFile = manager.resolveFile(
						criarStringConexao(servidorSFTP, usuario, senha,
								itemRemoto), criarOpcoesPadrao());

				if (!remoteFile.exists()) {
					bExiste = false;
					break;
				}
			}

			return bExiste;
		} catch (FileSystemException e) {
			throw new SFtpException(log, e.getMessage());
		} finally {
			manager.close();
		}
	}

	public void downloadArquivos(String diretorioLocal, String diretorioRemoto,
			List<String> arquivosRemotos) throws SFtpException {
		String arquivoLocal, itemRemoto;
		FileObject localFile, remoteFile;
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			manager.init();

			for (String arquivoRemoto : arquivosRemotos) {
				arquivoLocal = diretorioLocal + "/" + arquivoRemoto;
				itemRemoto = diretorioRemoto + "/" + arquivoRemoto;

				localFile = manager.resolveFile(arquivoLocal);

				remoteFile = manager.resolveFile(
						criarStringConexao(servidorSFTP, usuario, senha,
								itemRemoto), criarOpcoesPadrao());

				localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
			}

		} catch (FileSystemException e) {
			throw new SFtpException(log, e.getMessage());
		} finally {
			manager.close();
		}
	}

	public void uploadArquivos(String diretorioRemoto, String diretorioLocal,
			List<String> arquivosLocais) throws SFtpException {
		File file;
		String arquivoRemoto, itemLocal;
		FileObject localFile, remoteFile;
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			manager.init();

			for (String arquivoLocal : arquivosLocais) {
				arquivoRemoto = diretorioRemoto + "/" + arquivoLocal;
				itemLocal = diretorioLocal + "/" + arquivoLocal;

				file = new File(itemLocal);
				if (!file.exists()) {
					throw new SFtpException(log,
							"Arquivo local não encontrado!");
				}

				localFile = manager.resolveFile(file.getAbsolutePath());

				remoteFile = manager.resolveFile(
						criarStringConexao(servidorSFTP, usuario, senha,
								arquivoRemoto), criarOpcoesPadrao());

				remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
			}

		} catch (FileSystemException e) {
			throw new SFtpException(log, e.getMessage());
		} finally {
			manager.close();
		}
	}

	public void excluirArquivosRemotos(String diretorioRemoto,
			List<String> arquivosRemotos) throws SFtpException {
		FileObject remoteFile;
		String itemRemoto;
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			manager.init();

			for (String arquivoRemoto : arquivosRemotos) {
				itemRemoto = diretorioRemoto + "/" + arquivoRemoto;

				remoteFile = manager.resolveFile(
						criarStringConexao(servidorSFTP, usuario, senha,
								itemRemoto), criarOpcoesPadrao());

				if (remoteFile.exists()) {
					remoteFile.delete();
				}
			}
		} catch (FileSystemException e) {
			throw new SFtpException(log, e.getMessage());
		} finally {
			manager.close();
		}
	}

	public String getServidorSFTP() {
		return servidorSFTP;
	}

	public void setServidorSFTP(String servidorSFTP) {
		this.servidorSFTP = servidorSFTP;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
