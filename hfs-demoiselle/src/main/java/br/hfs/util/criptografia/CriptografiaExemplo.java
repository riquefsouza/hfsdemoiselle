package br.hfs.util.criptografia;

import javax.xml.bind.DatatypeConverter;

import br.hfs.util.criptografia.CriptografiaUtil.GeradorChave;
import br.hfs.util.criptografia.CriptografiaUtil.GeradorParChaves;

public class CriptografiaExemplo {
	
	public static void main(String[] args) {

		try {
			CriptografiaUtil cp = new CriptografiaUtil();
			
			System.out.println("Todos Provedores de Segurança");
			for (ProvedorSeguranca item : cp.listarProvedores()) {
				System.out.println(item);
			}
			System.out.println("----------");
			
			System.out.println("Todos Servicos Criptografia");
			for (String item : cp.getTodosServicosCriptografia()) {
				System.out.println(item);
			}
			System.out.println("----------");
			
			System.out.println("Geradores Chave");
			for (String item : cp.listarGeradoresChave()) {
				System.out.println(item);
			}
			System.out.println("----------");
			
			System.out.println("Geradores PAR Chave");
			for (String item : cp.listarGeradoresParChaves()) {
				System.out.println(item);
			}

			System.out.println("CRIPTOGRAFIA SIMETRICA");
			Criptografia cript = new Criptografia();
			cript.setFrase("Henrique Figueiredo de Souza".getBytes());
			
			for (GeradorChave item : GeradorChave.values()) {
				System.out.println("Chave: " + item); 
				
				cript = cp.criptografiaSimetrica(item, cript, true);
				System.out.println("textoCripto: " + cript.getCriptografadoToHexadecimal());
				
				cript = cp.criptografiaSimetrica(item, cript, false);
				System.out.println("textoDescripto: " + cript.getDescriptografadoToString());				
			}
			

			System.out.println("CRIPTOGRAFIA ASSIMETRICA");
			cript = new Criptografia();
			cript.setFrase("Henrique Figueiredo de Souza".getBytes());		
			
			for (GeradorParChaves item : GeradorParChaves.values()) {
				
				if (!item.equals(GeradorParChaves.DSA) && !item.equals(GeradorParChaves.DH) && !item.equals(GeradorParChaves.ECDSA)){ //Sem provedor
				
					System.out.println("Chave: " + item); 
				
					Criptografia cripto = cp.criptoAssimetrico(cript);
				
					System.out.println("chavePublica: " + DatatypeConverter.printBase64Binary(cripto.getChavePublica().getEncoded()));
					System.out.println("chavePrivada: " + DatatypeConverter.printBase64Binary(cripto.getChavePrivada().getEncoded()));
							
					System.out.println("textoCripto: " + cripto.getCriptografadoToHexadecimal());
					System.out.println("textoDescripto: " + cripto.getDescriptografadoToString());
				}
			}
				
		} catch (CriptografiaException e) {
			e.printStackTrace();
		}
		
	}

}
