package br.hfs.util.assinatura;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class AssinaturaExemplo {

	public static void main(String[] args) throws IOException,
			AssinaturaDigitalException {
		AssinaturaDigitalUtil adu = new AssinaturaDigitalUtil();

		File arquivo = new File(
				"C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros.pdf");
		byte[] dataToSign = FileUtils.readFileToByteArray(arquivo);

		File arqAssinado = new File(
				"C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros.pdf.p7s");
		
		byte[] encodedPKCS7 = adu.assinarPKCS7_Token(dataToSign);
		

		if (!arqAssinado.exists()) {
			FileUtils.writeByteArrayToFile(arqAssinado, encodedPKCS7);
		}
		
		/*
		for (String item : adu.listarAlgoritmosAssinatura()) {
			System.out.println(item);
		}

		File nomeStore = new File(
				"C:/Demoiselle/workspace/hfs-demoiselle/keystore/KeyStore.jks");
		byte[] dataStore = FileUtils.readFileToByteArray(nomeStore);

		// "C:/Demoiselle/workspace/hfs-demoiselle/certificado/arquivo_pkcs12.pfx";
		byte[] encodedPKCS7 = adu.assinarPKCS7(true, new ByteArrayInputStream(
				dataStore), "abcd1234", dataToSign);

		if (!arqAssinado.exists()) {
			FileUtils.writeByteArrayToFile(arqAssinado, encodedPKCS7);
		}
*/
		
		/*
		 * byte[] encodedPKCS7 = adu.assinarPKCS7_BouncyCastle("SHA1withRSA",
		 * new ByteArrayInputStream(dataStore), "trt1.jus.br", "abcd1234",
		 * dataToSign);
		 * 
		 * 
		 * if (!arqAssinado.exists()) {
		 * FileUtils.writeByteArrayToFile(arqAssinado, encodedPKCS7); }
		 */

		/*
		 * File arqAssinado = new File(
		 * "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros.pdf.p7s"); byte[]
		 * dataSign = FileUtils.readFileToByteArray(arqAssinado);
		 * System.out.println(adu.verificarPKCS7_BouncyCastle(dataSign));
		 */
	}

}
