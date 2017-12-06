package br.hfs.util.certificado;

import java.io.File;
import java.io.IOException;
import java.security.cert.Certificate;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.hfs.util.security.CertificadoDigitalVO;

public class CertificadoExemplo {

	public static void main(String[] args) {
/*
		try {
			 FileInputStream fis = new FileInputStream(new File("C:/Demoiselle/workspace/hfs-demoiselle/documentos/certificadoHenrique.cer"));
			 BufferedInputStream bis = new BufferedInputStream(fis);

			 CertificateFactory cf = CertificateFactory.getInstance("X.509");

			 while (bis.available() > 0) {
			    Certificate cert = cf.generateCertificate(bis);			    
			    System.out.println(cert.toString());
			 }
			 bis.close();
			 fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("------------------------------------------------------------------------------------");
	*/
		
		try {
			CertificadoUtil cUtil = new CertificadoUtil();
			
			/*
			cUtil.listarAliasesKeyStore(new File("C:/Demoiselle/workspace/hfs-demoiselle/keystore/KeyStore.jks"),"abcd1234");
			
			Certificate cert = cUtil.getCertificadoDoKeyStore(new File("C:/Demoiselle/workspace/hfs-demoiselle/keystore/KeyStore.jks"), "abcd1234", "trt1.jus.br");
			
			System.out.println(cert.getType());
			
			System.out.println("------------------------------------------------------------------------------------");
			cUtil.getCertificadosP7B(new File("C:/Demoiselle/workspace/hfs-demoiselle/documentos/certificadoHenrique.p7b"));	
			System.out.println("------------------------------------------------------------------------------------");
		
			byte[] conteudo = FileUtils.readFileToByteArray(new File("C:/Demoiselle/workspace/hfs-demoiselle/documentos/certificadoHenrique.cer"));		
			CertificadoX509 cx = cUtil.getCertificadoX509(conteudo);
			
			for (CertificadoDigitalVO item: cx.toLista()) {
				System.out.println(item);
			}

			System.out.println("------------------------------------------------------------------------------------");
			List<String> lista =  cUtil.listarAliasTokenSmart();
			for (String alias: lista) {
				System.out.println("Alias do TOKEN: " + alias);
				
				CertificadoX509 cx1 = cUtil.getCertificadoPorAliasWindows(alias);
				for (CertificadoDigitalVO item: cx1.toLista()) {
					System.out.println(item);
				}
			}
			*/

			CertificadoX509 cx1 = cUtil.getCertificadoTokenWindows();
			for (CertificadoDigitalVO item: cx1.toLista()) {
				System.out.println(item);
			}
			
		} catch (CertificadoException e) {
			e.printStackTrace();
		}		
	}


}
