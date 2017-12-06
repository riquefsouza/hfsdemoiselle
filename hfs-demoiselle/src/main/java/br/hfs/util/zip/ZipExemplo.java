package br.hfs.util.zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ZipExemplo {

	public static void main(String[] args) {
		try {
			ZipUtil zipUtil = new ZipUtil();

			List<String> lista = zipUtil.listarZIP("C:/Demoiselle/workspace/hfs-demoiselle/documentos/exemplo.zip");
			
			for (String item : lista) {
				System.out.println(item);
			}
			
			String nomeArquivoZip = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros.zip";
			String dirArquivos = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura";
			List<String> arquivos = new ArrayList<String>();
			arquivos.add("carros.pdf");
			
			zipUtil.criarZIP(nomeArquivoZip, dirArquivos, arquivos);
			
			
			byte[] entrada = FileUtils.readFileToByteArray(new File(dirArquivos+"/carros.pdf"));
			
			byte[] saida = zipUtil.comprimirByteArray(entrada);
			
			FileUtils.writeByteArrayToFile(new File(dirArquivos+"/carros.dfl"), saida);
			
			byte[] compactado = FileUtils.readFileToByteArray(new File(dirArquivos+"/carros.dfl"));
			
			byte[] descompactado = zipUtil.descomprimirByteArray(compactado);
			
			FileUtils.writeByteArrayToFile(new File(dirArquivos+"/carros-descompactado.pdf"), descompactado);
			
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
