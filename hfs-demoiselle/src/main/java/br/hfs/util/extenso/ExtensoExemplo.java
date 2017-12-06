package br.hfs.util.extenso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExtensoExemplo {

    public static void main(String[] args) throws NumberFormatException {
        // Converte do tipo BigDecimal para a String por extenso.
        ExtensoUtil teste = new ExtensoUtil(new BigDecimal(23455639.69));

        // Mostra o número informado no formato de valor monetário.
        System.out.println("Numero  : " + teste.DecimalFormat());

        // Mostra o número informado no formato de valor por extenso.
        System.out.println("Extenso : " + teste.toString());
        
        BigDecimal numero;
        List<Extenso> lista = new ArrayList<Extenso>();       
        for (int i = 0; i < 10; i++) {
        	numero = new BigDecimal(Math.random() * 100000);
        	teste.setNumber(numero);
        	lista.add(new Extenso(teste.DecimalFormat(), teste.toString()));
		}

        for (Extenso extenso : lista) {
        	System.out.println("Numero : " + extenso.getNumero());
        	System.out.println("Extenso : " + extenso.getExtenso());	
		}
    	

    }

}
