package br.hfs.util.report;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import br.gov.frameworkdemoiselle.report.Report;
import br.gov.frameworkdemoiselle.report.Type;
import br.gov.frameworkdemoiselle.report.annotation.Path;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.util.FileRenderer;
import br.hfs.business.UsuarioBC;

@ViewController
public class RelatorioMB implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@Path("reports/listarUsuarios.jasper")
	private Report relatorio;

	@Inject
	private FileRenderer renderer;

	@Inject
	private UsuarioBC usuarioBC;

	public Map<String, Object> parametros() {
		Map<String, Object> params = new HashMap<String, Object>();

		FacesContext fc = FacesContext.getCurrentInstance();  
		ServletContext sc = (ServletContext) fc.getExternalContext().getContext();  
		String caminho = sc.getRealPath(File.separator);
				
		params.put("IMAGEM", caminho + "/resources/images/logo.png");
		params.put("PARAMETRO1", "teste de parametro");
		return params;
	}
	
	public void mostrarPDF() {
		Map<String, Object> params = parametros();
		
		byte[] buffer = this.relatorio.export(usuarioBC.findAll(), params,
				Type.PDF);
		this.renderer.render(buffer, FileRenderer.ContentType.PDF,
				"relatorio.pdf");
	}
	
	public void mostrarODT() {
		Map<String, Object> params = parametros();
		
		byte[] buffer = this.relatorio.export(usuarioBC.findAll(), params,
				Type.ODT);
		this.renderer.render(buffer, FileRenderer.ContentType.ODT,
				"relatorio.odt");
	}
}
