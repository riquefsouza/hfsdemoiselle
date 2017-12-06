package br.hfs.util.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;

public class PdfExemplo2 {

	public static void main(String[] args) {
		BasicConfigurator.configure();

		PdfUtil pu = new PdfUtil();

		try {
			String vazio0 = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/vazio.pdf";
			byte[] arqVazio0 = FileUtils.readFileToByteArray(new File(vazio0));
			
			Set<PdfInfoFonte> item = pu.getInfoFonte(arqVazio0);
			System.out.println(item.toString());
						
			String vazio = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros.pdf";
			byte[] arqVazio = FileUtils.readFileToByteArray(new File(vazio));
			
			PdfInfo pdfinfo = pu.getInfo(arqVazio);
			System.out.println(pdfinfo);		

			byte[] saida0 = pu.setPDFA(arqVazio, vazio);			
			String fsaida0 = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros-PDFA.pdf";
			FileUtils.writeByteArrayToFile(new File(fsaida0), saida0);

			
			PdfPermissao permissao = new PdfPermissao();
			permissao.setTodasPermissoes(false);
			permissao.setPodeImprimir(true);
			byte[] saida = pu.mudarPermissoes(arqVazio, permissao);
			
			String fsaida = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros-sem-permissoes.pdf";
			FileUtils.writeByteArrayToFile(new File(fsaida), saida);
			
			
			PdfPermissao permissao2 = new PdfPermissao();
			permissao2.setSenhaUsuario("abcd1234");
			permissao2.setSenhaProprietario("abcd1234");
			permissao2.setTodasPermissoes(false);
			permissao2.setPodeImprimir(true);
			byte[] saida2 = pu.mudarPermissoes(arqVazio, permissao2);
			
			String fsaida2 = "C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros-sem-permissoes-com-senha.pdf";
			FileUtils.writeByteArrayToFile(new File(fsaida2), saida2);
			
		} catch (PdfException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
