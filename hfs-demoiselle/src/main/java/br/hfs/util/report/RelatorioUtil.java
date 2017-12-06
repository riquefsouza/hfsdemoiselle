package br.hfs.util.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class RelatorioUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final FacesContext fc = FacesContext.getCurrentInstance();
	private static final ServletContext sc = (ServletContext) fc
			.getExternalContext().getContext();
	private static final String caminho = sc.getRealPath(File.separator);

	public static void montaReport(HashMap<String, Object> parametros,
			JRDataSource jrDataSourse, String nomeJRXML, String tipo) {
		try {
			JasperReport relatorio = JasperCompileManager.compileReport(caminho
					+ nomeJRXML);
			JasperPrint impressao = JasperFillManager.fillReport(relatorio,
					parametros, jrDataSourse);
			gerar(impressao, tipo);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	private static void gerar(JasperPrint impressao, String tipo)
			throws JRException, IOException {
		byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
		if (tipo.equals("rtf")) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRRtfExporter saida = new JRRtfExporter();
			saida.setExporterInput(new SimpleExporterInput(impressao));
			saida.setExporterOutput(new SimpleWriterExporterOutput(baos));			
			//SimpleRtfReportConfiguration confReport = new SimpleRtfReportConfiguration(); 
			//SimpleRtfExporterConfiguration confExporter = new SimpleRtfExporterConfiguration();
			saida.exportReport();
			bytes = baos.toByteArray();
		}

		if (tipo.equals("xls")) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRXlsExporter xlsExporter = new JRXlsExporter();
			xlsExporter.setExporterInput(new SimpleExporterInput(impressao));
			xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
					baos));
			SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
			//SimpleXlsExporterConfiguration xlsExporterConfiguration = new SimpleXlsExporterConfiguration();
			xlsReportConfiguration.setOnePagePerSheet(true);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(false);
			xlsReportConfiguration.setDetectCellType(true);
			xlsReportConfiguration.setWhitePageBackground(false);
			xlsExporter.setConfiguration(xlsReportConfiguration);
			xlsExporter.exportReport();
			bytes = baos.toByteArray();
		}

		HttpServletResponse res = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		res.setContentType("application//" + tipo);
		res.setHeader("Content-disposition", "inline;filename=Relatorio."
				+ tipo);
		res.setHeader("Content-disposition", "attachment;filename=Relatorio."
				+ tipo);
		res.getOutputStream().write(bytes);
		res.getCharacterEncoding();
		FacesContext.getCurrentInstance().responseComplete();
	}
}