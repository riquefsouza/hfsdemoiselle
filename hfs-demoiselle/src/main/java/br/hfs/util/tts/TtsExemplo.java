package br.hfs.util.tts;

public class TtsExemplo {

	public static void main(String[] args) {
		TtsUtil tu = new TtsUtil();
		
		try {
			tu.inicializarFreeTTS("kevin16");
			//tu.falarFreeTTS("My name is Kevin");
			//tu.textoParaAudioWAV("My name is Kevin", "c:/temp/saida");
			String arq = "C:\\Demoiselle\\server\\jboss-7.1\\standalone\\deployments\\hfs-demoiselle.war\\temp\\KX9FDQ0Q8WMUPCZR8MSPLOIKK0DGI7XI";
			tu.textoParaAudioWAV("My name is Kevin", arq);
			tu.terminarFreeTTS();
		} catch (TtsException e) {
			e.printStackTrace();
		}
	}

}
