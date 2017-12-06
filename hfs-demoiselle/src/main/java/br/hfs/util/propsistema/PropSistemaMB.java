package br.hfs.util.propsistema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class PropSistemaMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public List<PropriedadeSistema> getPropriedadesSistema() {
		List<PropriedadeSistema> lista = new ArrayList<PropriedadeSistema>();
		Properties props = System.getProperties();
		PropriedadeSistema propSis;
		Enumeration<?> enum1 = props.propertyNames();
		for (; enum1.hasMoreElements();) {
			String propName = (String) enum1.nextElement();			
			String propValue = (String) props.get(propName);
			
			if (propName.equals("http.proxyPassword") || propName.equals("https.proxyPassword") ) {
				propSis = new PropriedadeSistema(propName, "");
			} else {
				propSis = new PropriedadeSistema(propName, propValue);				
			}
			lista.add(propSis);
		}
		Collections.sort(lista);
		return lista;
	}

}
