package br.hfs.util.rss;

import java.util.List;

public class RssExemplo {

	public static void main(String[] args) {
		try {
			RssUtil rs = new RssUtil();

			// String urlToRead = "http://feeds.feedburner.com/manishchhabra27";
			String urlToRead = "http://www.trtsp.jus.br/index.php/component/content/category/16-noticias-juridicas?layout=blog&format=feed&type=rss";
			//List<RssEntrada> lista = rs.lerRSS(urlToRead,
				//	"bravo.trtrio.gov.br", 8080, "henrique.souza", "");
			List<RssEntrada> lista = rs.lerRSS(urlToRead);
			for (RssEntrada rssEntrada : lista) {
				System.out.println(rssEntrada.toString());
			}
		} catch (RssException e) {
			e.printStackTrace();
		}
	}
}
