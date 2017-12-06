package br.hfs.util.feriado;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class FeriadoMB implements Serializable {
	private static final long serialVersionUID = 1L;

	private int anoInicial = 2010;
	private int anoFinal = 2020;
	private List<Feriado> lista;
	
	@Inject
	MessageContext messageContext;

	@Inject
	FeriadoUtil feriado;

	public int getAnoInicial() {
		return anoInicial;
	}

	public void setAnoInicial(int anoInicial) {
		this.anoInicial = anoInicial;
	}

	public int getAnoFinal() {
		return anoFinal;
	}

	public void setAnoFinal(int anoFinal) {
		this.anoFinal = anoFinal;
	}

	public List<Feriado> getLista() {
		return lista;
	}

	public void gerar(){
		try {
			lista = feriado.gerarFeriadosRecife(anoInicial, anoFinal);
		} catch (Exception e) {
			messageContext.add(e.getMessage());
		}		
	}
	
	@PostConstruct
    public void init() {
		gerar();
	}
	
}
