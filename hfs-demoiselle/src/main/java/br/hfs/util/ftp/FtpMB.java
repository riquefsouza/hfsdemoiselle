package br.hfs.util.ftp;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.apache.commons.net.ftp.FTPClient;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class FtpMB implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<FtpArquivo> listaArquivos;

	private String itemSelecionado;
	
	private String servidorFTP;
	private String usuarioFTP;
	private String senhaFTP; 
	private String dirInicialFTP;
	
	private boolean carregarProxy;	
	private String hostProxy;
	private int portProxy;
	private String loginProxy;
	private String senhaProxy;

	@Inject
	private FtpUtil ftpUtil;

	@Inject
	MessageContext messageContext;

	@PostConstruct
	public void init() {
		servidorFTP = "ftp.unicamp.br";
		usuarioFTP = "anonymous";
		senhaFTP = "riquefsouza@gmail.com"; 
		dirInicialFTP = "pub";
		
		carregarProxy = false;
		hostProxy = "bravo.trtrio.gov.br";
		portProxy = 8080;
		loginProxy = "henrique.souza";		
	}
	
	public void carregarComProxy() {
		try {
			if (!senhaProxy.isEmpty()) {
				FTPClient cliente = ftpUtil.ConectaServidorFTPProxy(servidorFTP,
						usuarioFTP, senhaFTP, dirInicialFTP, hostProxy, portProxy,
						loginProxy, senhaProxy);
				listaArquivos = ftpUtil.listaArquivosFTP(cliente, ".", true);
				ftpUtil.DesconectaServidorFTP(cliente);
			} else {
				messageContext.add("A senha está vazia!");
			}
		} catch (FtpException e) {
			messageContext.add(e.getMessage());
		}
	}
	
	public void carregar() {	
		try {
			FTPClient cliente = ftpUtil.ConectaServidorFTP(servidorFTP,
					usuarioFTP, senhaFTP, dirInicialFTP);
			listaArquivos = ftpUtil.listaArquivosFTP(cliente, ".", true);
			ftpUtil.DesconectaServidorFTP(cliente);
		} catch (FtpException e) {
			messageContext.add(e.getMessage());
		}

	}

	public void linkAcao(ActionEvent actionEvent) {
		messageContext.add(itemSelecionado);
	}

	public List<FtpArquivo> getListaArquivos() {
		return listaArquivos;
	}

	public String getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(String itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public String getServidorFTP() {
		return servidorFTP;
	}

	public void setServidorFTP(String servidorFTP) {
		this.servidorFTP = servidorFTP;
	}

	public String getUsuarioFTP() {
		return usuarioFTP;
	}

	public void setUsuarioFTP(String usuarioFTP) {
		this.usuarioFTP = usuarioFTP;
	}

	public String getSenhaFTP() {
		return senhaFTP;
	}

	public void setSenhaFTP(String senhaFTP) {
		this.senhaFTP = senhaFTP;
	}

	public String getDirInicialFTP() {
		return dirInicialFTP;
	}

	public void setDirInicialFTP(String dirInicialFTP) {
		this.dirInicialFTP = dirInicialFTP;
	}

	public boolean isCarregarProxy() {
		return carregarProxy;
	}

	public void setCarregarProxy(boolean carregarProxy) {
		this.carregarProxy = carregarProxy;
	}

	public String getHostProxy() {
		return hostProxy;
	}

	public void setHostProxy(String hostProxy) {
		this.hostProxy = hostProxy;
	}

	public int getPortProxy() {
		return portProxy;
	}

	public void setPortProxy(int portProxy) {
		this.portProxy = portProxy;
	}

	public String getLoginProxy() {
		return loginProxy;
	}

	public void setLoginProxy(String loginProxy) {
		this.loginProxy = loginProxy;
	}

	public String getSenhaProxy() {
		return senhaProxy;
	}

	public void setSenhaProxy(String senhaProxy) {
		this.senhaProxy = senhaProxy;
	}
}
