package br.hfs.util.docx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.convert.out.html.SdtToListSdtTagHandler;
import org.docx4j.convert.out.html.SdtWriter;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.model.fields.FieldUpdater;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class DocxUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("DocxUtil");

	public byte[] convertePDFtoDOC(byte[] arquivoPDF) throws DocxException {
		byte[] arquivoDOC = null;

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(arquivoPDF);

			Document pdfDocument = new Document(bis);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			DocSaveOptions saveOptions = new DocSaveOptions();
			saveOptions.setMode(DocSaveOptions.RecognitionMode.Flow);
			saveOptions.setRelativeHorizontalProximity(2.5f);
			saveOptions.setRecognizeBullets(true);
			saveOptions.setFormat(DocSaveOptions.DocFormat.Doc);

			pdfDocument.save(bos, saveOptions);

			bos.flush();
			arquivoDOC = bos.toByteArray();
			bos.close();
			
			pdfDocument.close();
			
		} catch (IOException e) {
			throw new DocxException(log, e.getMessage());
		}

		return arquivoDOC;
	}

	public byte[] convertePDFtoDOCX(byte[] arquivoPDF) throws DocxException {
		byte[] arquivoDOCX = null;

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(arquivoPDF);
			
			Document pdfDocument = new Document(bis);

			DocSaveOptions saveOptions = new DocSaveOptions();
			saveOptions.setMode(DocSaveOptions.RecognitionMode.Flow);
			saveOptions.setRelativeHorizontalProximity(2.5f);
			saveOptions.setRecognizeBullets(true);
			saveOptions.setFormat(DocSaveOptions.DocFormat.DocX);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			pdfDocument.save(bos, saveOptions);

			bos.flush();
			arquivoDOCX = bos.toByteArray();
			bos.close();
			
			pdfDocument.close();

		} catch (IOException e) {
			throw new DocxException(log, e.getMessage());
		}
		return arquivoDOCX;
	}
	
	public byte[] convertePDFtoDOCX_textoPuro(byte[] arquivoPDF) throws DocxException {
		byte[] arquivoDOCX = null;
		try {
			XWPFDocument doc = new XWPFDocument();
			PdfReader reader = new PdfReader(arquivoPDF);
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				TextExtractionStrategy strategy = parser.processContent(i,
						new SimpleTextExtractionStrategy());
				String text = strategy.getResultantText();
				XWPFParagraph p = doc.createParagraph();
				XWPFRun run = p.createRun();
				run.setText(text);
				run.addBreak(BreakType.PAGE);
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.write(out);

			out.flush();
			arquivoDOCX = out.toByteArray();
			out.close();

			reader.close();
			doc.close();
		} catch (IOException e) {
			throw new DocxException(log, e.getMessage());
		}
		return arquivoDOCX;
	}

	public byte[] converteDOCXtoPDF(byte[] arquivoDOCX) throws DocxException {
		byte[] arquivoPDF = null;
		try {
			// 1) Load DOCX into XWPFDocument
			// InputStream is = new FileInputStream(new
			// File("docx/ooxml.docx"));
			ByteArrayInputStream is = new ByteArrayInputStream(arquivoDOCX);
			XWPFDocument document = new XWPFDocument(is);

			// 2) Prepare Pdf options
			PdfOptions options = PdfOptions.create();

			// 3) Convert XWPFDocument to Pdf
			// OutputStream out = new FileOutputStream(new
			// File("pdf/ooxml.pdf"));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PdfConverter.getInstance().convert(document, out, options);

			out.flush();
			arquivoPDF = out.toByteArray();
			out.close();

		} catch (IOException e) {
			throw new DocxException(log, e.getMessage());
		}
		return arquivoPDF;
	}

	public byte[] converteDOCXtoHTML(String caminhoHTML, byte[] arquivoDOCX)
			throws DocxException {
		byte[] arquivoHTML = null;
		try {
			// 1) Load DOCX into XWPFDocument
			// InputStream is = new FileInputStream(new
			// File("docx/ooxml.docx"));
			ByteArrayInputStream is = new ByteArrayInputStream(arquivoDOCX);
			XWPFDocument document = new XWPFDocument(is);

			// 2) Prepare Html options
			XHTMLOptions options = XHTMLOptions.create();
			// Extract image
			File imageFolder = new File("html/images/ooxml/");
			imageFolder.getParentFile().mkdirs();
			options.setExtractor(new FileImageExtractor(imageFolder));
			// URI resolver
			options.URIResolver(new FileURIResolver(imageFolder));

			// 3) Convert XWPFDocument to HTML
			// OutputStream out = new FileOutputStream(new
			// File("html/ooxml.html"));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XHTMLConverter.getInstance().convert(document, out, options);

			out.flush();
			arquivoHTML = out.toByteArray();
			out.close();

		} catch (IOException e) {
			throw new DocxException(log, e.getMessage());
		}
		return arquivoHTML;
	}

	public byte[] converteDOCXtoPDF_viaDocx4j(byte[] arquivoDOCX)
			throws DocxException {
		byte[] arquivoPDF = null;
		try {
			String regex = null;
			// Windows:
			// String
			// regex=".*(calibri|camb|cour|arial|symb|times|Times|zapf).*";
			// regex=".*(calibri|camb|cour|arial|times|comic|georgia|impact|LSANS|pala|tahoma|trebuc|verdana|symbol|webdings|wingding).*";
			// Mac
			// String
			// regex=".*(Courier New|Arial|Times New Roman|Comic Sans|Georgia|Impact|Lucida Console|Lucida Sans Unicode|Palatino Linotype|Tahoma|Trebuchet|Verdana|Symbol|Webdings|Wingdings|MS Sans Serif|MS Serif).*";
			PhysicalFonts.setRegex(regex);

			// 1) Load DOCX into WordprocessingMLPackage
			// InputStream is = new FileInputStream(new
			// File("docx/ooxml.docx"));
			ByteArrayInputStream is = new ByteArrayInputStream(arquivoDOCX);
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
					.load(is);

			// 2) Prepare Pdf settings
			// PdfSettings pdfSettings = new PdfSettings();
			FieldUpdater updater = new FieldUpdater(wordMLPackage);
			updater.update(true);

			// 3) Convert WordprocessingMLPackage to Pdf
			// OutputStream out = new FileOutputStream(new
			// File("pdf/ooxml.pdf"));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// PdfConversion converter = new
			// org.docx4j.convert.out.pdf.viaXSLFO.Conversion(wordMLPackage);
			// converter.output(out, pdfSettings);

			Docx4J.toPDF(wordMLPackage, out);

			out.flush();
			arquivoPDF = out.toByteArray();
			out.close();

		} catch (IOException e) {
			throw new DocxException(log, e.getMessage());
		} catch (Docx4JException e) {
			throw new DocxException(log, e.getMessage());
		}
		return arquivoPDF;
	}

	public byte[] converteDOCXtoHTML_viaDocx4j(String caminhoHTML,
			byte[] arquivoDOCX) throws DocxException {
		byte[] arquivoHTML = null;
		try {
			// 1) Load DOCX into WordprocessingMLPackage
			// InputStream is = new FileInputStream(new
			// File("docx/ooxml.docx"));
			ByteArrayInputStream is = new ByteArrayInputStream(arquivoDOCX);
			// WordprocessingMLPackage wordMLPackage =
			// WordprocessingMLPackage.load(is);
			WordprocessingMLPackage wordMLPackage = Docx4J.load(is);

			// 2) Prepare HTML settings
			HTMLSettings htmlSettings = Docx4J.createHTMLSettings();

			htmlSettings.setImageDirPath(caminhoHTML + "_files");
			htmlSettings.setImageTargetUri(caminhoHTML.substring(caminhoHTML
					.lastIndexOf("/") + 1) + "_files");
			htmlSettings.setWmlPackage(wordMLPackage);
			SdtWriter.registerTagHandler("HTML_ELEMENT",
					new SdtToListSdtTagHandler());

			// 3) Convert WordprocessingMLPackage to HTML
			// OutputStream out = new FileOutputStream(new
			// File("html/ooxml.html"));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Docx4jProperties.setProperty(
					"docx4j.Convert.Out.HTML.OutputMethodXML", true);
			Docx4J.toHTML(htmlSettings, out, Docx4J.FLAG_EXPORT_PREFER_XSL);

			// Docx4J.toHTML(wordMLPackage, imageDirPath, imageTargetUri, out);

			out.flush();
			arquivoHTML = out.toByteArray();
			out.close();

		} catch (IOException e) {
			throw new DocxException(log, e.getMessage());
		} catch (Docx4JException e) {
			throw new DocxException(log, e.getMessage());
		}
		return arquivoHTML;
	}

	public File converteDOCXtoPDF_viaOffice(File caminhoOffice, File arquivoDOCX) {
		File arquivoPDF = null;

		// 1) Start LibreOffice in headless mode.
		OfficeManager officeManager = null;
		try {
			// new File("C:/Program Files/LibreOffice 3.5")
			officeManager = new DefaultOfficeManagerConfiguration()
					.setOfficeHome(caminhoOffice).buildOfficeManager();
			officeManager.start();

			// 2) Create JODConverter converter
			OfficeDocumentConverter converter = new OfficeDocumentConverter(
					officeManager);

			// 3) Create PDF
			converter.convert(arquivoDOCX, arquivoPDF);

		} finally {
			// 4) Stop LibreOffice in headless mode.
			if (officeManager != null) {
				officeManager.stop();
			}
		}

		return arquivoPDF;
	}

	public File converteDOCXtoHTML_viaOffice(File caminhoOffice,
			File arquivoDOCX) {
		File arquivoHTML = null;

		// 1) Start LibreOffice in headless mode.
		OfficeManager officeManager = null;
		try {
			// new File("C:/Program Files/LibreOffice 3.5")
			officeManager = new DefaultOfficeManagerConfiguration()
					.setOfficeHome(caminhoOffice).buildOfficeManager();
			officeManager.start();

			// 2) Create JODConverter converter
			OfficeDocumentConverter converter = new OfficeDocumentConverter(
					officeManager);

			// 3) Create HTML
			converter.convert(arquivoDOCX, arquivoHTML);

		} finally {
			// 4) Stop LibreOffice in headless mode.
			if (officeManager != null) {
				officeManager.stop();
			}
		}

		return arquivoHTML;
	}

}
