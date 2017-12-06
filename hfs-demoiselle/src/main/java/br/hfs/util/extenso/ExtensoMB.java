package br.hfs.util.extenso;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class ExtensoMB implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Extenso> lista;
	
	private int quantidade;
	
	@Inject
	ExtensoUtil extenso;

	public List<Extenso> getLista() {
		return lista;
	}
	
	public void gerar(){
		BigDecimal numero;
		lista = new ArrayList<Extenso>();
		for (int i = 0; i < quantidade; i++) {
			numero = new BigDecimal(Math.random() * 100000);
			extenso.setNumber(numero);
			lista.add(new Extenso(extenso.DecimalFormat(), extenso.toString()));
		}		
	}
	
	@PostConstruct
    public void init() {
		quantidade = 20;
		gerar();
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
