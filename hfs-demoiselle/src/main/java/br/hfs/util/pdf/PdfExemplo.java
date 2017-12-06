package br.hfs.util.pdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;

public class PdfExemplo {

	public static void main(String[] args) {
		BasicConfigurator.configure();

		PdfUtil pu = new PdfUtil();

		try {
			/*
			System.out.println(pu.pdfToString(
					"C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros.pdf", 1));

			List<PdfAssinatura> lista = pu.mostrarAssinaturas("abcd1234",
					"C:/temp/assinado.pdf");

			for (PdfAssinatura pdfAssinatura : lista) {
				System.out.println(pdfAssinatura.toString());
			}

			pu.criarCampoAssinaturaNaoAssinado("c:/VM/vazio.pdf", 380, 780,
					200, 50);
			*/
			
		for (int i = 1; i <=3; i++) {
			String vazio = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/vazio"+i+".pdf";
			byte[] arqVazio = FileUtils.readFileToByteArray(new File(vazio));
			//pu.criarPDF(vazio, 10);
			
			SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
			
			String[] texto = {
            "Documento digitalmente assinado em "+ sm.format(new Date()) +", nos termos da Lei 11.419m de 19-12-2006.",
            "Identificador: 10000007890 http://www.trt1.jus.br/web/guest/conferencia-assinatura-eletronica" };

			
			//String saida = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/saida"+i+".pdf";
			byte[] arqSaida = pu.textoVerticalPDF(arqVazio, texto);
			
			
			//String arquivoImagem = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/imagemAssinatura.jpg";
			byte[] arquivoImagem = IOUtils.toByteArray(PdfExemplo.class.getResourceAsStream("imagemAssinatura.jpg"));
			
			arqSaida = pu.addImagemPDF(arqSaida, arquivoImagem, 20, 25);
			/*
			pu.criarAssinatura(
					"C:/Demoiselle/workspace/hfs-demoiselle/certificado/arquivo_pkcs12.pfx",
					"abcd1234",
					"c:/temp/Como Instalar Programas no Ubuntu 12.10.pdf", null);
			
			
			//File nomeStore = new File(
				//	"C:/Demoiselle/workspace/hfs-demoiselle/certificado/arquivo_pkcs12.pfx");
			File nomeStore = new File(
						"C:/Demoiselle/workspace/hfs-demoiselle/keystore/KeyStore.jks");
			byte[] dataStore = FileUtils.readFileToByteArray(nomeStore);
			
			File arquivo = new File(
					"C:/Demoiselle/workspace/hfs-demoiselle/assinatura/saida"+i+".pdf");
			byte[] dataToSign = FileUtils.readFileToByteArray(arquivo);
				*/
			//FilenameUtils.removeExtension(arquivo.getAbsolutePath());
			
			File arqAssinado = new File(
					"C:/Demoiselle/workspace/hfs-demoiselle/assinatura/saida"+i+".pdf");

			/*
			byte[] pdfAssinado = pu.criarAssinaturaVisivel(true, new ByteArrayInputStream(
					dataStore), "abcd1234", new ByteArrayInputStream(dataToSign), null,
					null);
			*/
			
			byte[] pdfAssinado = pu.criarAssinaturaVisivelViaToken(new ByteArrayInputStream(arqSaida), null,
					null);
			
			//if (!arqAssinado.exists()) {
				FileUtils.writeByteArrayToFile(arqAssinado, pdfAssinado);
			//}
			
		}	
			
/*
			byte[] fonte;
			try {
				fonte = FileUtils.readFileToByteArray(new File(
						"c:/VM/Como Instalar Programas no Ubuntu 12.10.pdf"));

				List<PdfPagina> lista = pu.pdfToTexto(fonte);

				for (PdfPagina pdfPagina : lista) {
					for (String item : pdfPagina.getTexto()) {
						System.out
								.println(pdfPagina.getNumero() + " - " + item);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
*/
		} catch (PdfException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
