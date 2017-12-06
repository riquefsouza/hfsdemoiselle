package br.hfs.view.apiexemplos;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
public class MapaView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String caminho;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ec = context.getExternalContext();
		caminho = ec.getRequestScheme() + "://" + ec.getRequestServerName()
				+ ":" + ec.getRequestServerPort() + ec.getRequestContextPath()
				+ "/mapa";
	}

	public String getCaminho() {
		return caminho;
	}
}
