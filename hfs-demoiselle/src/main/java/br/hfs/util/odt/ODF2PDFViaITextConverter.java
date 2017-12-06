package br.hfs.util.odt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.odftoolkit.odfdom.converter.core.ODFConverterException;
import org.odftoolkit.odfdom.converter.pdf.PdfConverter;
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

import fr.opensagres.xdocreport.converter.MimeMapping;
import fr.opensagres.xdocreport.converter.MimeMappingConstants;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.OptionsHelper;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.converter.internal.AbstractConverterNoEntriesSupport;
import fr.opensagres.xdocreport.core.utils.StringUtils;

public class ODF2PDFViaITextConverter extends AbstractConverterNoEntriesSupport
		implements MimeMappingConstants {

	private static final ODF2PDFViaITextConverter INSTANCE = new ODF2PDFViaITextConverter();

	public static ODF2PDFViaITextConverter getInstance() {
		return INSTANCE;
	}

	public void convert(InputStream in, OutputStream out, Options options)
			throws XDocConverterException {
		try {
			OdfTextDocument odfDocument = OdfTextDocument.loadDocument(in);
			PdfConverter.getInstance().convert(odfDocument, out,
					toPdfOptions(options));
		} catch (ODFConverterException e) {
			throw new XDocConverterException(e);
		} catch (IOException e) {
			throw new XDocConverterException(e);
		} catch (Exception e) {
			throw new XDocConverterException(e);
		}
	}

	public PdfOptions toPdfOptions(Options options) {
		if (options == null) {
			return null;
		}
		Object value = options.getSubOptions(PdfOptions.class);
		if (value instanceof PdfOptions) {
			return (PdfOptions) value;
		}
		PdfOptions pdfOptions = PdfOptions.create();
		// Populate font encoding
		String fontEncoding = OptionsHelper.getFontEncoding(options);
		if (StringUtils.isNotEmpty(fontEncoding)) {
			pdfOptions.fontEncoding(fontEncoding);
		}
		return pdfOptions;
	}

	public MimeMapping getMimeMapping() {
		return PDF_MIME_MAPPING;
	}

	@Override
	public boolean isDefault() {
		return true;
	}

}
