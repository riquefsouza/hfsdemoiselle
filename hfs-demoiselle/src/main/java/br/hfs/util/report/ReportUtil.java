package br.hfs.util.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.apache.commons.io.FileUtils;

import br.hfs.util.StringUtil;
import br.hfs.util.VisitarDiretorio;
import br.hfs.util.zip.ZipException;
import br.hfs.util.zip.ZipUtil;

public final class ReportUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("ReportUtil");

	private ZipUtil zipUtil;

	public ReportUtil() {
		zipUtil = new ZipUtil();
	}

	public enum OpcaoGeracao {
		PADRAO, ARQUIVO
	}
	
	public enum FormatoArquivo {
		PDF, HTML, XLS, RTF
	}

	public void relatorioPDF(HttpServletResponse res, String arquivoPDF) {
		try {
			byte[] pdf = null;
			if (arquivoPDF != null) {
				pdf = FileUtils.readFileToByteArray(new File(arquivoPDF));

				// Diz que vai enviar um arquivo do tipo PDF
				res.setContentType("application/pdf");

				// Diz o tamanho dos dados
				res.setContentLength(pdf.length);

				// Escreve no browser
				ServletOutputStream ouputStream = res.getOutputStream();
				ouputStream.write(pdf, 0, pdf.length);
				ouputStream.flush();
				ouputStream.close();
			}
		} catch (IOException e) {
			log.severe("Erro de entrada/saída ao gerar relatório, "
					+ e.getMessage());
		}
	}

	/**
	 * Realiza a conversão de um relatório jasper para PDF.
	 * 
	 * @param response
	 *            o HttpServletResponse do servlet
	 * @param caminhoRelatorio
	 *            o caminho onde se encontra o relatorio
	 * @param paramsRelatorio
	 *            o parâmetros contido no Jasper relatório
	 * @param fonteDados
	 *            o JRDataSource contendo os dados do relatório
	 * @throws ReportException
	 */
	public void relatorioPDF(HttpServletResponse res, String jasperRelatorio,
			Map<String, Object> paramsRelatorio, JRDataSource fonteDados)
			throws ReportException {
		try {
			byte[] pdf = null;

			pdf = JasperRunManager.runReportToPdf(jasperRelatorio,
					paramsRelatorio, fonteDados);

			// Diz que vai enviar um arquivo do tipo PDF
			res.setContentType("application/pdf");

			// Diz o tamanho dos dados
			res.setContentLength(pdf.length);

			// Escreve no browser
			ServletOutputStream ouputStream;

			ouputStream = res.getOutputStream();
			ouputStream.write(pdf, 0, pdf.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			throw new ReportException(log,
					"Erro de entrada/saída ao gerar relatório");
		} catch (JRException e) {
			throw new ReportException(log, "Erro ao gerar relatório");
		}
	}

	public void gerarRelatorioParaArquivo(FormatoArquivo tipo, File arquivo,
			String jasperRelatorio, Map<String, Object> paramsRelatorio,
			JRDataSource fonteDados) throws ReportException {
		try {
			// InputStream arquivoJasper = Rotinas.getTextoDentroJar(
			// RotinasRelatorio.class, jasperRelatorio);

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperRelatorio, paramsRelatorio, fonteDados);
			if (tipo == FormatoArquivo.PDF) {
				JasperExportManager.exportReportToPdfFile(jasperPrint,
						arquivo.getPath());
			} else if (tipo == FormatoArquivo.HTML) {
				JasperExportManager.exportReportToHtmlFile(jasperPrint,
						arquivo.getPath());
			} else if (tipo == FormatoArquivo.XLS) {
				FileOutputStream arqRel = new FileOutputStream(arquivo);
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setExporterInput(new SimpleExporterInput(
						jasperPrint));
				exporterXLS
						.setExporterOutput(new SimpleOutputStreamExporterOutput(
								arqRel));
				SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
				// SimpleXlsExporterConfiguration xlsExporterConfiguration = new
				// SimpleXlsExporterConfiguration();
				xlsReportConfiguration.setOnePagePerSheet(true);
				xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(false);
				xlsReportConfiguration.setDetectCellType(true);
				xlsReportConfiguration.setWhitePageBackground(false);
				exporterXLS.setConfiguration(xlsReportConfiguration);
				exporterXLS.exportReport();
			} else if (tipo == FormatoArquivo.RTF) {
				FileOutputStream arqRel = new FileOutputStream(arquivo);
				JRRtfExporter exporterRTF = new JRRtfExporter();
				exporterRTF.setExporterInput(new SimpleExporterInput(
						jasperPrint));
				exporterRTF.setExporterOutput(new SimpleWriterExporterOutput(
						arqRel));
				exporterRTF.exportReport();
			}
		} catch (IOException e) {
			throw new ReportException(log,
					"Erro de entrada/saída ao gerar relatório para arquivo");
		} catch (JRException e) {
			throw new ReportException(log,
					"Erro ao gerar relatório para arquivo");
		}
	}

	public void relatorioPorFormato(HttpServletResponse res,
			String jasperRelatorio, Map<String, Object> paramsRelatorio,
			JRDataSource fonteDados, FormatoArquivo tipo, String diretorio,
			boolean compactado, boolean escreverNoBrowser)
			throws ReportException {
		try {
			byte[] conteudo = null;

			switch (tipo) {
			case PDF:
				conteudo = geraConteudo(res, jasperRelatorio, paramsRelatorio,
						fonteDados, tipo, diretorio, compactado, "pdf",
						"application/pdf", escreverNoBrowser);
				break;
			case HTML:
				conteudo = geraConteudo(res, jasperRelatorio, paramsRelatorio,
						fonteDados, tipo, diretorio, compactado, "html",
						"text/html", escreverNoBrowser);
				break;
			case XLS:
				conteudo = geraConteudo(res, jasperRelatorio, paramsRelatorio,
						fonteDados, tipo, diretorio, compactado, "xls",
						"application/vnd.ms-excel", escreverNoBrowser);
				break;
			case RTF:
				conteudo = geraConteudo(res, jasperRelatorio, paramsRelatorio,
						fonteDados, tipo, diretorio, compactado, "rtf",
						"application/rtf", escreverNoBrowser);
				break;
			}

			if (escreverNoBrowser) {
				res.setContentLength(conteudo.length);
				ServletOutputStream ouputStream = res.getOutputStream();
				ouputStream.write(conteudo, 0, conteudo.length);
				ouputStream.flush();
				ouputStream.close();
			}
		} catch (IOException e) {
			throw new ReportException(log,
					"Erro de entrada/saída ao gerar relatório");
		}
	}

	private byte[] geraConteudo(HttpServletResponse res,
			String jasperRelatorio, Map<String, Object> paramsRelatorio,
			JRDataSource fonteDados, FormatoArquivo formato, String diretorio,
			boolean compactado, String extensao, String tipo,
			boolean excluirArquivo) throws IOException, ReportException {
		byte[] conteudo;
		String sArquivo;
		File arquivo;

		try {
			sArquivo = StringUtil.getArquivoRandom(extensao);
			arquivo = new File(diretorio, sArquivo);

			if (!compactado) {
				gerarRelatorioParaArquivo(formato, arquivo, jasperRelatorio,
						paramsRelatorio, fonteDados);
				conteudo = FileUtils.readFileToByteArray(arquivo);
				res.setContentType(tipo);
			} else {
				gerarRelatorioParaArquivo(formato, arquivo, jasperRelatorio,
						paramsRelatorio, fonteDados);

				String arquivoZIP = diretorio + "/" + sArquivo + ".zip";
				if (formato == FormatoArquivo.HTML) {
					String dirResto = diretorio + "/" + sArquivo + "_files";

					if (VisitarDiretorio.getInstancia().ListarDiretorio(
							dirResto)) {
						ArrayList<File> lista = VisitarDiretorio.getInstancia()
								.getListaFile();
						List<String> arquivos = new ArrayList<String>();
						List<String> dirs = new ArrayList<String>();

						dirs.add("");
						arquivos.add(sArquivo);

						for (int j = 0; j < lista.size(); j++) {
							dirs.add(lista.get(j).getParentFile().getName());
							arquivos.add(lista.get(j).getName());
						}
						zipUtil.criarZIP(arquivoZIP, diretorio, dirs, arquivos);

						for (File file : lista) {
							file.delete();
						}
						File subdir = new File(dirResto);
						subdir.delete();
					}

				} else {
					List<String> arquivos = new ArrayList<String>();
					arquivos.add(sArquivo);
					zipUtil.criarZIP(arquivoZIP, diretorio, arquivos);
				}
				arquivo.delete();

				arquivo = new File(arquivoZIP);
				conteudo = FileUtils.readFileToByteArray(arquivo);
				res.setContentType("application/zip");
			}
			if (excluirArquivo) {
				arquivo.delete();
			}
		} catch (ZipException e) {
			throw new ReportException(log, e.getMessage());
		}

		return conteudo;
	}

}
