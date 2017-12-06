package br.hfs.util.docx;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;

import br.hfs.util.directory.DirectoryUtil;

public class DocxExemplo {

	public static void main(String[] args) {
		BasicConfigurator.configure();
/*
		DocxUtil dxu = new DocxUtil();
		DirectoryUtil du = new DirectoryUtil(
				DirectoryUtil.filtrarPorExtensao(".docx"));

		try {
			String nome;
			byte[] arquivoDOCX, arquivoPDF;
			try {
				List<File> arquivos = du.getArquivos(new File(
						"C:\\Demoiselle\\workspace\\hfs-demoiselle\\docx"));
				for (File arq : arquivos) {
				    long start = System.currentTimeMillis();
			        					
					arquivoDOCX = FileUtils.readFileToByteArray(arq);

					arquivoPDF = dxu.converteDOCXtoPDF(arquivoDOCX);

					nome = arq.getAbsolutePath().substring(0,arq.getAbsolutePath().lastIndexOf('.'))+".pdf";
					FileUtils.writeByteArrayToFile(new File(nome), arquivoPDF);

					System.out.println("DOCX para PDF XDocReport: "  + (System.currentTimeMillis() - start) + "ms");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (DocxException e) {
			e.printStackTrace();
		}
*/		

		DocxUtil dxu = new DocxUtil();
		DirectoryUtil du = new DirectoryUtil(
				DirectoryUtil.filtrarPorExtensao(".pdf"));

		try {
			String nome;
			byte[] arquivoDOCX, arquivoPDF;
			try {
				List<File> arquivos = du.getArquivos(new File(
						"C:\\Demoiselle\\workspace\\hfs-demoiselle\\pdf"));
				for (File arq : arquivos) {
				    long start = System.currentTimeMillis();
			        					
					arquivoPDF = FileUtils.readFileToByteArray(arq);

					arquivoDOCX = dxu.convertePDFtoDOCX(arquivoPDF);

					nome = arq.getAbsolutePath().substring(0,arq.getAbsolutePath().lastIndexOf('.'))+".docx";
					FileUtils.writeByteArrayToFile(new File(nome), arquivoDOCX);

					System.out.println("PDF para DOCX Aspose: "  + (System.currentTimeMillis() - start) + "ms");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (DocxException e) {
			e.printStackTrace();
		}
		
	}

}
