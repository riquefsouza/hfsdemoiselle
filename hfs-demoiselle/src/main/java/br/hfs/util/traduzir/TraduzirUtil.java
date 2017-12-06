package br.hfs.util.traduzir;

import java.io.Serializable;
import java.util.logging.Logger;

public class TraduzirUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("TraduzirUtil");

/*
	public TraduzirUtil(String hostProxy,
			int portProxy, final String loginProxy, final String senhaProxy){
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
				hostProxy, portProxy));

		Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication(loginProxy,
						senhaProxy.toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);
	}
*/
	
/*	
	public String traduzirTexto(String texto, String daLingua,
			String paraLingua) {
		Translator translate = Translator.getInstance();
		String saida = translate.translate(texto, daLingua, paraLingua);
		return saida;
	}

	public String detectarLinguaDoTexto(String texto) {
		Translator translate = Translator.getInstance();
		String prefix = translate.detect(texto);
		return prefix;
	}

	public String getNomeDaLingua(String texto) {
		Translator translate = Translator.getInstance();
		Language language = Language.getInstance();
		String prefixLanguage = translate.detect(texto);
		String nameLanguage = language.getNameLanguage(prefixLanguage);
		return nameLanguage;
	}

	public String getNomeTraduzidoDaLingua(String texto, String daLingua) {
		Translator translate = Translator.getInstance();
		Language language = Language.getInstance();
		String prefixLanguage = translate.detect(texto);
		String translateLanguage = language.getNameLanguage(prefixLanguage,
				daLingua);
		return translateLanguage;
	}

	public void reproduzTextoTraduzido(String texto, String daLingua)
			throws TraduzirException {
		try {
			Audio audio = Audio.getInstance();
			InputStream sound = audio.getAudio(texto, daLingua);
			audio.play(sound);
		} catch (IOException e) {
			throw new TraduzirException(log, e.getMessage());
		} catch (JavaLayerException e) {
			throw new TraduzirException(log, e.getMessage());
		}
	}
*/	
}
