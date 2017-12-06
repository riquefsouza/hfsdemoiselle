package br.hfs.util.security;

import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.BasicConfigurator;

import br.gov.frameworkdemoiselle.certificate.criptography.AsymmetricAlgorithmEnum;
import br.gov.frameworkdemoiselle.certificate.criptography.DigestAlgorithmEnum;
import br.gov.frameworkdemoiselle.certificate.criptography.SymmetricAlgorithmEnum;

public class SegurancaExemplo {
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		SegurancaUtil segurancaUtil = new SegurancaUtil();
		
		System.out.println("HASH SIMPLES");
		System.out.println(segurancaUtil.HashSimplesHex(
				"conteudo original 23".getBytes(), DigestAlgorithmEnum.SHA_1));
		System.out.println("-------------------------------------");

		System.out.println("HASH DE ARQUIVO");
		System.out.println(segurancaUtil.HashArquivoHex(new File(
				"C:/Demoiselle/workspace/hfs-demoiselle/documentos/chart.png"), DigestAlgorithmEnum.SHA_1));
		System.out.println("-------------------------------------");

		System.out.println("CRIPTOGRAFIA SIMETRICA");
		CriptografiaParams params = new CriptografiaParams();
		params.setFrase("conteudo original simetrico".getBytes());
		params.setAlgoritmoSimetrico(SymmetricAlgorithmEnum.AES);

		params = segurancaUtil.criptografiaSimetrica(params, true);
		System.out.println("Criptografia Simetrica: "
				+ params.getCriptografadoToHexadecimal());

		System.out.println("Chave unica: " + params.getChaveUnica());
		System.out.println("Chave unica Hexadecimal: "
				+ Hex.encodeHexString(params.getChaveUnica().getEncoded()));
		System.out.println("Chave unica Formato: "
				+ params.getChaveUnica().getFormat());
		System.out.println("Chave unica Algoritmo: "
				+ params.getChaveUnica().getAlgorithm());

		params = segurancaUtil.criptografiaSimetrica(params, false);
		System.out.println("Descriptografia Simetrica: "
				+ params.getDescriptografadoToString());
		System.out.println("-------------------------------------");

		
		System.out.println("CRIPTOGRAFIA ASSIMETRICA");
		CriptografiaParams params2 = new CriptografiaParams();		
		
		params2.setFrase("conteudo original assimetrico".getBytes());
		params2.setAlgoritmoAssimetrico(AsymmetricAlgorithmEnum.RSA);

		params2 = segurancaUtil.criptografiaAssimetrica(params2, true);
		System.out.println("Criptografia Assimetrica: "
				+ params.getCriptografadoToHexadecimal());

		System.out.println("Chave Pública: " + params2.getChavePublica());
		System.out.println("Chave Pública Hexadecimal: "
				+ Hex.encodeHexString(params2.getChavePublica().getEncoded()));
		System.out.println("Chave Pública Formato: "
				+ params2.getChavePublica().getFormat());
		System.out.println("Chave Pública Algoritmo: "
				+ params2.getChavePublica().getAlgorithm());

		System.out.println("Chave Privada: " + params2.getChavePrivada());
		System.out.println("Chave Privada Hexadecimal: "
				+ Hex.encodeHexString(params2.getChavePrivada().getEncoded()));
		System.out.println("Chave Privada Formato: "
				+ params2.getChavePrivada().getFormat());
		System.out.println("Chave Privada Algoritmo: "
				+ params2.getChavePrivada().getAlgorithm());
		
		params2 = segurancaUtil.criptografiaAssimetrica(params2, false);
		System.out.println("Descriptografia Assimetrica: "
				+ params2.getDescriptografadoToString());
		System.out.println("-------------------------------------");
		
		
		System.out.println("CERTIFICADO DIGITAL");
		CertificadoDigital cert = segurancaUtil
				.carregarCertificadoDigitalDeArquivo("C:/Demoiselle/workspace/hfs-demoiselle/documentos/certificadoHenrique.cer", false);

		for (CertificadoDigitalVO item : cert.toLista()) {
			System.out.println(item.getItem() + ": " + item.getDescricao());
		}

		System.out.println("CERTIFICADO DIGITAL DE TOKEN");
		CertificadoDigital cert1 = segurancaUtil
				.carregarCertificadoDigitalDeToken(false);
		for (CertificadoDigitalVO item : cert1.toLista()) {
			System.out.println(item.getItem() + ": " + item.getDescricao());
		}
			
		System.out.println("-------------------------------------");		
		System.out.println("ASSINATURA DIGITAL");
		
		File arqKeyStore = new File("C:/Demoiselle/workspace/hfs-demoiselle/keystore/KeyStore.jks");
		String apelidoKeyStore = "trt1.jus.br";
		File arquivo = new File("C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros.pdf");
		File arqAssinado = new File("C:/Demoiselle/workspace/hfs-demoiselle/assinatura/carros.pdf.p7s");
		
		try {
			segurancaUtil.assinaturaDigitalFormatoPKCS7(arqKeyStore, "abcd1234", apelidoKeyStore, arquivo, arqAssinado);
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
