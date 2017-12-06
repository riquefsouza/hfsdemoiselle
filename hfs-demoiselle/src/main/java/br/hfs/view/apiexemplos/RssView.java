package br.hfs.view.apiexemplos;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.rss.RssEntrada;
import br.hfs.util.rss.RssException;
import br.hfs.util.rss.RssUtil;

@ManagedBean
public class RssView implements Serializable {
	private static final long serialVersionUID = 1L;
		
	private String urlToRead;
	
	private boolean carregarProxy;	
	private String hostProxy;
	private int portProxy;
	private String loginProxy;
	private String senhaProxy;
	
	private List<RssEntrada> listaItems;

	@Inject
	private RssUtil rssUtil;

	@Inject
	private MessageContext messageContext;

	@PostConstruct
	public void init() {
		carregarProxy = false;
		urlToRead = "http://www.trtsp.jus.br/index.php/component/content/category/16-noticias-juridicas?layout=blog&format=feed&type=rss";
		hostProxy = "bravo.trtrio.gov.br";
		portProxy = 8080;
		loginProxy = "henrique.souza";
	}

	public void carregarComProxy() {
		try {
			if (!senhaProxy.isEmpty()) {
				listaItems = rssUtil.lerRSS(urlToRead, hostProxy, portProxy,
						loginProxy, senhaProxy);
			} else {
				messageContext.add("A senha está vazia!");
			}
		} catch (RssException e) {
			messageContext.add(e.getMessage());
		}
	}

	public void carregar() {
		try {
			listaItems = rssUtil.lerRSS(urlToRead);
		} catch (RssException e) {
			messageContext.add(e.getMessage());
		}

	}

	public List<RssEntrada> getListaItems() {
		return listaItems;
	}

	public String getUrlToRead() {
		return urlToRead;
	}

	public void setUrlToRead(String urlToRead) {
		this.urlToRead = urlToRead;
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

	public boolean isCarregarProxy() {
		return carregarProxy;
	}

	public void setCarregarProxy(boolean carregarProxy) {
		this.carregarProxy = carregarProxy;
	}

}
