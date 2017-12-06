package br.hfs.util.odt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import fr.opensagres.xdocreport.converter.IURIResolver;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.OptionsHelper;
import fr.opensagres.xdocreport.converter.XDocConverterException;

public class OdtUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("OdtUtil");

	public byte[] converteODTtoPDF(byte[] arquivoODT) throws OdtException {
		byte[] arquivoPDF = null;

		try {
			Options options = Options.getFrom("ODT");
			OptionsHelper.setFontEncoding(options, "UTF-8");
			//PdfOptions pdfOptions = ODF2PDFViaITextConverter.getInstance().toPdfOptions(options);		
			
			ByteArrayInputStream is = new ByteArrayInputStream(arquivoODT);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ODF2PDFViaITextConverter.getInstance().convert(is, out, options);
			
			out.flush();
			arquivoPDF = out.toByteArray();
			out.close();
			
		} catch (XDocConverterException e) {
			throw new OdtException(log, e.getMessage());
		} catch (IOException e) {
			throw new OdtException(log, e.getMessage());
		}
		
		return arquivoPDF;
	}

	public byte[] converteODTtoHTML(byte[] arquivoODT) throws OdtException {
		byte[] arquivoHTML = null;

		try {
			Options options = Options.getFrom("ODT");
			OptionsHelper.setURIResolver(options, new IURIResolver() {
				public String resolve(String uri) {
					return null;
				}
			});
			//XHTMLOptions xhtmlOptions = ODF2XHTMLConverter.getInstance().toXHTMLOptions(options);

			ByteArrayInputStream is = new ByteArrayInputStream(arquivoODT);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ODF2XHTMLConverter.getInstance().convert(is, out, options);
			
			out.flush();
			arquivoHTML = out.toByteArray();
			out.close();
		} catch (XDocConverterException e) {
			throw new OdtException(log, e.getMessage());
		} catch (IOException e) {
			throw new OdtException(log, e.getMessage());
		}
		
		return arquivoHTML;
	}

	/*
	 * jodconverter 2.2.1
	 * 
	public void docToPdf(String arquivoDOC, String arquivoPDF)
			throws OdtException {
		try {
			File inputFile = new File(arquivoDOC);
			File outputFile = new File(arquivoPDF);

			// connect to an OpenOffice.org instance running on port 8100
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(
					8100);
			connection.connect();

			// convert
			DocumentConverter converter = new OpenOfficeDocumentConverter(
					connection);
			converter.convert(inputFile, outputFile);

			// close the connection
			connection.disconnect();
		} catch (ConnectException e) {
			throw new OdtException(log, e.getMessage());
		}
	}
	*/

	public void OdtFromTemplate(String arquivoModeloODT,
			Map<String, String> params, String arquivoODT) throws OdtException {
		try {
			DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
			DocumentTemplate template = documentTemplateFactory
					.getTemplate(new File(arquivoModeloODT));
			// Map<String, String> data = new HashMap<String, String>();
			// data.put("var", "value");
			template.createDocument(params, new FileOutputStream(arquivoODT));
		} catch (FileNotFoundException e) {
			throw new OdtException(log, e.getMessage());
		} catch (IOException e) {
			throw new OdtException(log, e.getMessage());
		} catch (DocumentTemplateException e) {
			throw new OdtException(log, e.getMessage());
		}
	}
}
