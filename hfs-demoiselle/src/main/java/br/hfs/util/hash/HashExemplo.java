package br.hfs.util.hash;

import org.apache.commons.codec.binary.Hex;

import br.hfs.util.hash.HashUtil.TipoDigest;

public class HashExemplo {

	public static void main(String[] args) {
		try {
			HashUtil hash = new HashUtil();
			
			for (String item: hash.listarAlgoritmosHash()) {
				System.out.println(item);
			}
			
			System.out.println(
					Hex.encodeHexString(
					hash.getHash(TipoDigest.SHA512, "NOVO TESTE DE AGAIN!".getBytes())));
		} catch (HashException e) {
			e.printStackTrace();
		}
	}
}
