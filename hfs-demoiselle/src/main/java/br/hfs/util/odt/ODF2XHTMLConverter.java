package br.hfs.util.odt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.odftoolkit.odfdom.converter.core.ODFConverterException;
import org.odftoolkit.odfdom.converter.xhtml.XHTMLOptions;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

import fr.opensagres.xdocreport.converter.IURIResolver;
import fr.opensagres.xdocreport.converter.MimeMapping;
import fr.opensagres.xdocreport.converter.MimeMappingConstants;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.OptionsHelper;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.converter.internal.AbstractConverterNoEntriesSupport;

public class ODF2XHTMLConverter extends AbstractConverterNoEntriesSupport
		implements MimeMappingConstants {

	private static final ODF2XHTMLConverter INSTANCE = new ODF2XHTMLConverter();

	public static ODF2XHTMLConverter getInstance() {
		return INSTANCE;
	}

	public void convert(InputStream in, OutputStream out, Options options)
			throws XDocConverterException {
		try {
			OdfTextDocument odfDocument = OdfTextDocument.loadDocument(in);
			org.odftoolkit.odfdom.converter.xhtml.XHTMLConverter.getInstance()
					.convert(odfDocument, out, toXHTMLOptions(options));
		} catch (ODFConverterException e) {
			throw new XDocConverterException(e);
		} catch (IOException e) {
			throw new XDocConverterException(e);
		} catch (Exception e) {
			throw new XDocConverterException(e);
		}
	}

	public XHTMLOptions toXHTMLOptions(Options options) {
		if (options == null) {
			return null;
		}
		Object value = options.getSubOptions(XHTMLOptions.class);
		if (value instanceof XHTMLOptions) {
			return (XHTMLOptions) value;
		}
		XHTMLOptions xhtmlOptions = XHTMLOptions.create();
		final IURIResolver resolver = OptionsHelper.getURIResolver(options);
		if (resolver != null) {
			xhtmlOptions
					.URIResolver(new org.odftoolkit.odfdom.converter.core.IURIResolver() {
						public String resolve(String uri) {
							return resolver.resolve(uri);
						}
					});
		}
		return xhtmlOptions;
	}

	public MimeMapping getMimeMapping() {
		return XHTML_MIME_MAPPING;
	}

	@Override
	public boolean isDefault() {
		return true;
	}

}
