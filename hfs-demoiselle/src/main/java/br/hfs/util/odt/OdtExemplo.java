package br.hfs.util.odt;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;

import br.hfs.util.directory.DirectoryUtil;

public class OdtExemplo {

	public static void main(String[] args) {
		BasicConfigurator.configure();

		OdtUtil odu = new OdtUtil();
		DirectoryUtil du = new DirectoryUtil(
				DirectoryUtil.filtrarPorExtensao(".odt"));

		try {
			String nome;
			byte[] arquivoODT, arquivoPDF;
			try {
				List<File> arquivos = du.getArquivos(new File(
						"C:\\Demoiselle\\workspace\\hfs-demoiselle\\odt"));
				int i = 1;
				for (File arq : arquivos) {
					if (i!=2) {
						long start = System.currentTimeMillis();
						
						arquivoODT = FileUtils.readFileToByteArray(arq);
						
						arquivoPDF = odu.converteODTtoPDF(arquivoODT);
						
						nome = arq.getAbsolutePath().substring(0,arq.getAbsolutePath().lastIndexOf('.'))+".pdf";
						FileUtils.writeByteArrayToFile(new File(nome), arquivoPDF);
											
						System.out.println("ODT para PDF XDocReport: "  + (System.currentTimeMillis() - start) + "ms");
					}
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (OdtException e) {
			e.printStackTrace();
		}
	}

}
