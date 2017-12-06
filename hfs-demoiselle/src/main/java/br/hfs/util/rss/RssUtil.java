package br.hfs.util.rss;

import java.io.IOException;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class RssUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("RssUtil");

	public List<RssEntrada> lerRSS(String urlToRead) throws RssException {
		SyndFeed feed = lerFeed(urlToRead);
		return carregarRssEntrada(feed);
	}

	public List<SyndEntry> lerSyndEntries(String urlToRead) throws RssException {
		SyndFeed feed = lerFeed(urlToRead);
		List<SyndEntry> entries = feed.getEntries();
		return entries;
	}

	public SyndFeed lerFeed(String urlToRead) throws RssException {
		try {
			URL url = new URL(urlToRead);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setRequestMethod("GET");

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(conn));

			return feed;
		} catch (MalformedURLException e) {
			throw new RssException(log, e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new RssException(log, e.getMessage());
		} catch (IOException e) {
			throw new RssException(log, e.getMessage());
		} catch (FeedException e) {
			throw new RssException(log, e.getMessage());
		}

	}

	public List<RssEntrada> lerRSS(String urlToRead, String hostProxy,
			int portProxy, final String loginProxy, final String senhaProxy)
			throws RssException {

		SyndFeed feed = lerFeed(urlToRead, hostProxy, portProxy, loginProxy,
				senhaProxy);

		return carregarRssEntrada(feed);
	}

	private List<RssEntrada> carregarRssEntrada(SyndFeed feed) {
		RssEntrada entrada;
		List<RssEntrada> lista = new ArrayList<RssEntrada>();
		List<SyndEntry> entries = feed.getEntries();
		Iterator<SyndEntry> itEntries = entries.iterator();

		while (itEntries.hasNext()) {
			SyndEntry entry = itEntries.next();
			entrada = new RssEntrada();
			entrada.setTitulo(entry.getTitle());
			entrada.setLink(entry.getLink());
			entrada.setAutor(entry.getAuthor());
			entrada.setDataPublicacao(entry.getPublishedDate());
			entrada.setDescricao(entry.getDescription().getValue());
			lista.add(entrada);
		}

		return lista;
	}

	public List<SyndEntry> lerSyndEntries(String urlToRead, String hostProxy,
			int portProxy, final String loginProxy, final String senhaProxy)
			throws RssException {
		SyndFeed feed = lerFeed(urlToRead, hostProxy, portProxy, loginProxy,
				senhaProxy);
		List<SyndEntry> entries = feed.getEntries();
		return entries;
	}

	public SyndFeed lerFeed(String urlToRead, String hostProxy, int portProxy,
			final String loginProxy, final String senhaProxy)
			throws RssException {
		try {
			Proxy proxy = usarProxy(hostProxy, portProxy, loginProxy,
					senhaProxy);

			URL url = new URL(urlToRead);
			HttpURLConnection conn = (HttpURLConnection) url
					.openConnection(proxy);
			// conn.setRequestMethod("GET");

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(conn));

			return feed;
		} catch (MalformedURLException e) {
			throw new RssException(log, e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new RssException(log, e.getMessage());
		} catch (IOException e) {
			throw new RssException(log, e.getMessage());
		} catch (FeedException e) {
			throw new RssException(log, e.getMessage());
		}

	}

	private Proxy usarProxy(String hostProxy, int portProxy,
			final String loginProxy, final String senhaProxy) {
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				hostProxy, portProxy));

		Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication(loginProxy,
						senhaProxy.toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);
		return proxy;
	}
}
