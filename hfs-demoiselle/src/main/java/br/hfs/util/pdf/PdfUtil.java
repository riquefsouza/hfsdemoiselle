package br.hfs.util.pdf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDCIDFont;
import org.apache.pdfbox.pdmodel.font.PDCIDFontType2;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.Matrix;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.type.BadFieldValueException;
import org.apache.xmpbox.xml.XmpSerializer;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.StoreException;

import br.hfs.util.pdf.assinatura.CriarAssinatura;
import br.hfs.util.pdf.assinatura.CriarAssinaturaVisivel;
import br.hfs.util.pdf.assinatura.PdfAssinatura;
import br.hfs.util.pdf.assinatura.TSAClient;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PdfUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("PdfUtil");

	public void criarPDF(String arquivoPDF, int qtdPaginas)
			throws PdfException {		
		try {			
			PDDocument doc = new PDDocument();
			for (int i = 0; i < qtdPaginas; i++) {
				//PDFont font = PDType1Font.HELVETICA;
	            PDPage page = new PDPage();
	            page.setMediaBox(PDRectangle.A4);
	            
	            if (i % 2 == 0 )
	            	page.setRotation(270);
	            	
		        doc.addPage(page);			
			}
	        doc.save(arquivoPDF);
	        doc.close();
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		}
	}
	
	public String pdfToString(String arquivoPDF, int pagina)
			throws PdfException {
		String spagina = "";
		try {
			PdfReader reader = new PdfReader(arquivoPDF);
			spagina = PdfTextExtractor.getTextFromPage(reader, 2);
			// System.out.println("Is this document tampered: "+reader.isTampered());
			// System.out.println("Is this document encrypted: "+reader.isEncrypted());
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		}
		return spagina;
	}

	public List<PdfPagina> pdfToTexto(byte[] arquivoPDF) throws PdfException {
		List<PdfPagina> spagina = new ArrayList<PdfPagina>();
		List<String> linhas;
		String texto;
		try {
			PdfReader reader = new PdfReader(arquivoPDF);
			for (int i = 0; i < reader.getNumberOfPages(); i++) {
				texto = PdfTextExtractor.getTextFromPage(reader, i + 1);
				
				if (texto.indexOf(System.lineSeparator()) == -1){
					linhas = Arrays.asList(texto.split("\n"));
				} else {
					linhas = Arrays.asList(texto.split(System.lineSeparator()));
				}
				
				spagina.add(new PdfPagina(i + 1, linhas));
			}
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		}
		return spagina;
	}

	public String pdfToTexto(File arquivoPDF) throws PdfException {
		String texto = "";
		PDDocument document = null;
		try {
			try {
				document = PDDocument.load(arquivoPDF);
				PDFTextStripper stripper = new PDFTextStripper();
				texto = stripper.getText(document);
			} finally {
				if (document != null) {
					document.close();
				}
			}
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		}
		return texto;
	}

	public void criarCampoAssinaturaNaoAssinado(String arquivoPDF,
			float posicaoX, float posicaoY, float largura, float altura)
			throws PdfException {
		try {
			// Create a new document with an empty page.
			PDDocument document = new PDDocument();
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);

			// Adobe Acrobat uses Helvetica as a default font and
			// stores that under the name '/Helv' in the resources dictionary
			PDFont font = PDType1Font.HELVETICA;
			PDResources resources = new PDResources();
			resources.put(COSName.getPDFName("Helv"), font);

			// Add a new AcroForm and add that to the document
			PDAcroForm acroForm = new PDAcroForm(document);
			document.getDocumentCatalog().setAcroForm(acroForm);

			// Add and set the resources and default appearance at the form
			// level
			acroForm.setDefaultResources(resources);

			// Acrobat sets the font size on the form level to be
			// auto sized as default. This is done by setting the font size to
			// '0'
			String defaultAppearanceString = "/Helv 0 Tf 0 g";
			acroForm.setDefaultAppearance(defaultAppearanceString);

			// --- end of general AcroForm stuff ---

			// Create empty signature field, it will get the name "Signature1"
			PDSignatureField signatureField = new PDSignatureField(acroForm);
			PDAnnotationWidget widget = signatureField.getWidgets().get(0);
			// PDRectangle rect = new PDRectangle(50, 650, 200, 50);
			PDRectangle rect = new PDRectangle(posicaoX, posicaoY, largura,
					altura);
			widget.setRectangle(rect);
			widget.setPage(page);
			page.getAnnotations().add(widget);

			acroForm.getFields().add(signatureField);

			document.save(arquivoPDF);
			document.close();
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		}
	}

	public byte[] criarAssinatura(boolean JKS_ou_PKCS12, InputStream nomeStore,
			String senhaPIN, InputStream arquivoPDF, String tsaUrl) throws PdfException {
		byte[] pdfAssinado = null;
		
		try {
			KeyStore keystore;
			
			if (JKS_ou_PKCS12) {
				keystore = KeyStore.getInstance("JKS");
			} else {
				keystore = KeyStore.getInstance("PKCS12");
			}
			char[] password = senhaPIN.toCharArray(); // TODO use Java 6
														// java.io.Console.readPassword
			keystore.load(nomeStore, password);

			// TSA client
			TSAClient tsaClient = null;
			if (tsaUrl != null) {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				tsaClient = new TSAClient(new URL(tsaUrl), null, null, digest);
			}

			// sign PDF
			CriarAssinatura signing = new CriarAssinatura(keystore, password);

			/*
			File inFile = new File(arquivoPDF);
			String name = inFile.getName();
			String substring = name.substring(0, name.lastIndexOf('.'));

			File outFile = new File(inFile.getParent(), substring
					+ "_assinado.pdf");
			signing.signDetached(inFile, outFile, tsaClient);
			*/
			ByteArrayOutputStream signedDocumentFile = new ByteArrayOutputStream();
			
			signing.signDetached(arquivoPDF, signedDocumentFile, tsaClient);
			
			signedDocumentFile.flush();
			pdfAssinado = signedDocumentFile.toByteArray();
			signedDocumentFile.close();
		} catch (UnrecoverableKeyException e) {
			throw new PdfException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new PdfException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new PdfException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new PdfException(log, e.getMessage());
		} catch (FileNotFoundException e) {
			throw new PdfException(log, e.getMessage());
		} catch (MalformedURLException e) {
			throw new PdfException(log, e.getMessage());
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		}
		
		return pdfAssinado;
	}
	
	public byte[] gerarImagemAssinatura(String nome,
			String nomeDistinto, Date dataHora) throws IOException {
		//String dh = DateFormatUtils.format(dataHora, "dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		BufferedImage image = new BufferedImage(800, 100,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics2D = image.createGraphics();
		graphics2D.setBackground(Color.WHITE);
		graphics2D.clearRect(0, 0, 800, 100);
		graphics2D.setColor(Color.BLACK);

		// FontMetrics fontMetrics = graphics2D.getFontMetrics();
		// fontMetrics.getAscent()

		graphics2D.setFont(new Font("Helvetica", Font.TRUETYPE_FONT, 30));
		String txt = nome.toUpperCase();
		graphics2D.drawString(txt.substring(0, 19), 10, 40);
		graphics2D.drawString(txt.substring(20), 10, 80);

		graphics2D.setFont(new Font("Helvetica", Font.TRUETYPE_FONT, 11));
		txt = "Assinado de forma digital por " + nome.toUpperCase();
		int y = 15;
		graphics2D.drawString(txt, 420, y);

		txt = nomeDistinto;
		if (txt.length() > 60) {
			graphics2D.drawString(txt.substring(0, 60), 420, y + 15);
		} else {
			graphics2D.drawString(txt, 420, y + 15);
		}
		if (txt.length() > (60*2)) {
			graphics2D.drawString(txt.substring(60, 60 * 2), 420, y + (2 * 15));
		} else if (txt.length() > 60) {
			graphics2D.drawString(txt.substring(60, txt.length()), 420, y + (2 * 15));
		}
		if (txt.length() > (60*3)) {
			graphics2D.drawString(txt.substring(60 * 2, 60 * 3), 420, y + (3 * 15));
		} else if (txt.length() > (60*2)) {
			graphics2D.drawString(txt.substring(60 * 2, txt.length()), 420, y + (3 * 15));
		}
		if (txt.length() > (60*4)) {
			graphics2D.drawString(txt.substring(60 * 3), 420, y + (4 * 15));
		}

		txt = "Data: " + sm.format(new Date());
		graphics2D.drawString(txt, 420, y + (5 * 15));

		graphics2D.dispose();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "png", os);

		return os.toByteArray();
	}

	public byte[] criarAssinaturaVisivel(boolean JKS_ou_PKCS12, InputStream nomeStore,
			String senhaPIN, InputStream arquivoPDF, byte[] imagemAssinatura,
			String tsaUrl) throws PdfException {
		// generate with
		// keytool -storepass 123456 -storetype PKCS12 -keystore file.p12
		// -genkey -alias client -keyalg RSA
		byte[] pdfAssinado = null;
		
		try {			
			KeyStore keystore;
			
			if (JKS_ou_PKCS12) {
				keystore = KeyStore.getInstance("JKS");
			} else {
				keystore = KeyStore.getInstance("PKCS12");
			}
			char[] pin = senhaPIN.toCharArray(); // TODO use Java 6
														// java.io.Console.readPassword
			keystore.load(nomeStore, pin);
						
			//File ksFile = new File(arquivoKeystorePKCS12);
			//KeyStore keystore = KeyStore.getInstance("PKCS12",CriarAssinaturaVisivel.BCPROVIDER);
			//char[] pin = senhaPIN.toCharArray();
			//keystore.load(new FileInputStream(ksFile), pin);
			
			// TSA client
			TSAClient tsaClient = null;
			if (tsaUrl != null) {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				tsaClient = new TSAClient(new URL(tsaUrl), null, null, digest);
			}

			InputStream image;
			int page = 1;
			//for (int page = 1; page <= qtdPaginas; page++) {

				if (imagemAssinatura==null) {
					image = certificadoParaImagem(keystore);	
				} else {
					image = new ByteArrayInputStream(imagemAssinatura);
				}

				CriarAssinaturaVisivel signing = new CriarAssinaturaVisivel(
						keystore, pin.clone());

				signing.setvisibleSignDesigner(arquivoPDF, 0, 0, -50, image, page);
				signing.setVisibleSignatureProperties("name", "location",
						"Security", 0, page, true);
				
				arquivoPDF.reset();
				
				//if (page == qtdPaginas) {
					ByteArrayOutputStream signedDocumentFile = new ByteArrayOutputStream();
					
					signing.signPDF(arquivoPDF, signedDocumentFile, tsaClient);
					
					signedDocumentFile.flush();
					pdfAssinado = signedDocumentFile.toByteArray();
					signedDocumentFile.close();
				//}
			//}
			
		} catch (UnrecoverableKeyException e) {
			throw new PdfException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new PdfException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new PdfException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new PdfException(log, e.getMessage());
		} catch (FileNotFoundException e) {
			throw new PdfException(log, e.getMessage());
		} catch (MalformedURLException e) {
			throw new PdfException(log, e.getMessage());
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		}
		
		return pdfAssinado;
	}

	public byte[] criarAssinaturaVisivelViaToken(InputStream arquivoPDF, byte[] imagemAssinatura,
			String tsaUrl) throws PdfException {
		// generate with
		// keytool -storepass 123456 -storetype PKCS12 -keystore file.p12
		// -genkey -alias client -keyalg RSA
		byte[] pdfAssinado = null;
		
		try {			
			KeyStore keystore;
			
			keystore = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			//char[] pin = senhaPIN.toCharArray(); // TODO use Java 6
														// java.io.Console.readPassword
			keystore.load(null, null);
						
			//File ksFile = new File(arquivoKeystorePKCS12);
			//KeyStore keystore = KeyStore.getInstance("PKCS12",CriarAssinaturaVisivel.BCPROVIDER);
			//char[] pin = senhaPIN.toCharArray();
			//keystore.load(new FileInputStream(ksFile), pin);
			
			// TSA client
			TSAClient tsaClient = null;
			if (tsaUrl != null) {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				tsaClient = new TSAClient(new URL(tsaUrl), null, null, digest);
			}

			InputStream image;
			int page = 1;
			//for (int page = 1; page <= qtdPaginas; page++) {

				if (imagemAssinatura==null) {
					image = certificadoParaImagem(keystore);	
				} else {
					image = new ByteArrayInputStream(imagemAssinatura);
				}

				CriarAssinaturaVisivel signing = new CriarAssinaturaVisivel(
						keystore);

				signing.setvisibleSignDesigner(arquivoPDF, 0, 0, -50, image, page);
				signing.setVisibleSignatureProperties("name", "location",
						"Security", 0, page, true);
				
				arquivoPDF.reset();
				
				//if (page == qtdPaginas) {
					ByteArrayOutputStream signedDocumentFile = new ByteArrayOutputStream();
					
					signing.signPDF(arquivoPDF, signedDocumentFile, tsaClient);
					
					signedDocumentFile.flush();
					pdfAssinado = signedDocumentFile.toByteArray();
					signedDocumentFile.close();
				//}
			//}
			
		} catch (UnrecoverableKeyException e) {
			throw new PdfException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new PdfException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new PdfException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new PdfException(log, e.getMessage());
		} catch (FileNotFoundException e) {
			throw new PdfException(log, e.getMessage());
		} catch (MalformedURLException e) {
			throw new PdfException(log, e.getMessage());
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		} catch (NoSuchProviderException e) {
			throw new PdfException(log, e.getMessage());
		}
		
		return pdfAssinado;
	}
	
	private InputStream certificadoParaImagem(KeyStore keystore)
			throws KeyStoreException, PdfException, IOException {
		Enumeration<String> aliases = keystore.aliases();
		String aliaz = "";
		while (aliases.hasMoreElements()) {
			aliaz = aliases.nextElement();
			if (keystore.isKeyEntry(aliaz)) {
				break;
			}
		}
		if (aliaz.isEmpty()){
			throw new PdfException(log, "Certificado Digital não existe!");
		}			
		X509Certificate cx = (X509Certificate) keystore.getCertificate(aliaz);

		String[] sujeito = cx.getSubjectX500Principal().getName().split(",");
		String nome = "";
		for (String item : sujeito) {
			if (item.startsWith("CN=")){
				nome = item.substring(item.indexOf('=')+1);
				break;
			}
		}				
		String nomeDistinto = cx.getSubjectDN().getName();
		
		byte[] img = gerarImagemAssinatura(nome, nomeDistinto, Calendar.getInstance().getTime());
		InputStream image = new ByteArrayInputStream(img);
		
		return image;
	}

	public List<PdfAssinatura> mostrarAssinaturas(String senha,
			String arquivoPDF) throws PdfException {

		List<PdfAssinatura> lista = new ArrayList<PdfAssinatura>();
		PdfAssinatura pdfa;

		String password = senha;
		String infile = arquivoPDF;
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(infile), password);
			for (PDSignature sig : document.getSignatureDictionaries()) {
				COSDictionary sigDict = sig.getCOSObject();
				COSString contents = (COSString) sigDict
						.getDictionaryObject(COSName.CONTENTS);

				// download the signed content
				FileInputStream fis = new FileInputStream(infile);
				byte[] buf = sig.getSignedContent(fis);
				fis.close();

				pdfa = new PdfAssinatura();
				pdfa.setNome(sig.getName());
				pdfa.setData(sig.getSignDate().getTime());

				String subFilter = sig.getSubFilter();
				if (subFilter != null) {
					if (subFilter.equals("adbe.pkcs7.detached")) {
						pdfa.setValidoPKCS7(verificarPKCS7(buf, contents, sig,
								pdfa.getCertsX509()));

						// TODO check certificate chain, revocation lists,
						// timestamp...
					} else if (subFilter.equals("adbe.pkcs7.sha1")) {
						// example: PDFBOX-1452.pdf
						COSString certString = (COSString) sigDict
								.getDictionaryObject(COSName.CONTENTS);
						byte[] certData = certString.getBytes();
						CertificateFactory factory = CertificateFactory
								.getInstance("X.509");
						ByteArrayInputStream certStream = new ByteArrayInputStream(
								certData);
						Collection<? extends Certificate> certs = factory
								.generateCertificates(certStream);

						for (Certificate item : certs) {
							pdfa.getCertificados().add(item);
						}

						byte[] hash = MessageDigest.getInstance("SHA1").digest(
								buf);
						pdfa.setValidoPKCS7(verificarPKCS7(hash, contents, sig,
								pdfa.getCertsX509()));

						// TODO check certificate chain, revocation lists,
						// timestamp...
					} else if (subFilter.equals("adbe.x509.rsa_sha1")) {
						// example: PDFBOX-2693.pdf
						COSString certString = (COSString) sigDict
								.getDictionaryObject(COSName.getPDFName("Cert"));
						byte[] certData = certString.getBytes();
						CertificateFactory factory = CertificateFactory
								.getInstance("X.509");
						ByteArrayInputStream certStream = new ByteArrayInputStream(
								certData);
						Collection<? extends Certificate> certs = factory
								.generateCertificates(certStream);

						for (Certificate item : certs) {
							pdfa.getCertificados().add(item);
						}

						// TODO verify signature
					} else {
						throw new PdfException(log,
								"Tipo de Certificado Desconhecido: "
										+ subFilter);
					}
				} else {
					throw new PdfException(log,
							"Faltando subfiltro para o dictionário do certificado");
				}

				lista.add(pdfa);
			}
		} catch (CMSException e) {
			throw new PdfException(log, e.getMessage());
		} catch (OperatorCreationException e) {
			throw new PdfException(log, e.getMessage());
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new PdfException(log, e.getMessage());
		} catch (StoreException e) {
			throw new PdfException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new PdfException(log, e.getMessage());
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					throw new PdfException(log, e.getMessage());
				}
			}
		}

		return lista;
	}

	/**
	 * Verify a PKCS7 signature.
	 *
	 * @param byteArray
	 *            the byte sequence that has been signed
	 * @param contents
	 *            the /Contents field as a COSString
	 * @param sig
	 *            the PDF signature (the /V dictionary)
	 * @throws CertificateException
	 * @throws CMSException
	 * @throws StoreException
	 * @throws OperatorCreationException
	 */
	private boolean verificarPKCS7(byte[] byteArray, COSString contents,
			PDSignature sig, List<X509Certificate> certsX509)
			throws CMSException, CertificateException, StoreException,
			OperatorCreationException {
		// inspiration:
		// http://stackoverflow.com/a/26702631/535646
		// http://stackoverflow.com/a/9261365/535646
		CMSProcessable signedContent = new CMSProcessableByteArray(byteArray);
		CMSSignedData signedData = new CMSSignedData(signedContent,
				contents.getBytes());
		Store<?> certificatesStore = signedData.getCertificates();
		Collection<SignerInformation> signers = signedData.getSignerInfos()
				.getSigners();
		SignerInformation signerInformation = signers.iterator().next();
		Collection<?> matches = certificatesStore.getMatches(signerInformation
				.getSID());
		X509CertificateHolder certificateHolder = (X509CertificateHolder) matches
				.iterator().next();
		X509Certificate certFromSignedData = new JcaX509CertificateConverter()
				.getCertificate(certificateHolder);
		// System.out.println("certFromSignedData: " + certFromSignedData);
		certsX509.add(certFromSignedData);
		certFromSignedData.checkValidity(sig.getSignDate().getTime());

		return signerInformation
				.verify(new JcaSimpleSignerInfoVerifierBuilder()
						.build(certFromSignedData));
	}


	
    public byte[] textoVerticalPDF(byte[] arquivoPDF, String[] texto) 
    		throws PdfException 
    {
    	byte[] arquivoSaida = null;
    	int posicaoX;
    	int posicaoY;
        PDDocument doc = null;
        try
        {
        	if (texto!= null && texto.length > 0) {
	            //doc = PDDocument.load( new File(arquivoPDF) );
        		doc = PDDocument.load(arquivoPDF);
	
	            //PDFont font = PDType1Font.HELVETICA_BOLD;
	            PDFont font = PDType1Font.TIMES_ROMAN;
	            float fontSize = 12.0f;

	            for( PDPage page : doc.getPages() ) {
	                PDRectangle pageSize = page.getMediaBox();
	                float stringWidth = font.getStringWidth( texto[0] )*fontSize/1000f;
	                
	                int rotation = page.getRotation();
	                boolean rotate = rotation == 90 || rotation == 270;
	                float pageWidth = rotate ? pageSize.getHeight() : pageSize.getWidth();
	                float pageHeight = rotate ? pageSize.getWidth() : pageSize.getHeight();
	                float centerX = rotate ? pageHeight/2f : (pageWidth - stringWidth)/2f;
	                float centerY = rotate ? (pageWidth - stringWidth)/2f : pageHeight/2f;
	                
	                PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true);
	                contentStream.beginText();
	
	                contentStream.setFont( font, fontSize );
	
	                if (rotate){
	                	posicaoX = 500;
	                	posicaoY = 320;
	                	contentStream.setTextMatrix(Matrix.getRotateInstance(Math.PI, centerY + posicaoY, centerX + posicaoX));
	                } else {
	                    //contentStream.setTextMatrix(Matrix.getTranslateInstance(centerX, centerY));
	                	
	                	//paisagem
	                	if (pageWidth > pageHeight) {
		                	posicaoX = 620;
		                	posicaoY = 220;
	                	} else {
		                	posicaoX = 500;
		                	posicaoY = 200;
	                	}
	                	contentStream.setTextMatrix(Matrix.getRotateInstance(Math.PI / 2, centerX + posicaoX, centerY - posicaoY));
	                }
	
	                for (int i = 0; i < texto.length; i++) {
	                    contentStream.showText(texto[i]);
	                    if (texto.length > 1) {
	                    	contentStream.newLineAtOffset(0, -15);
	                    }
					}	
	                contentStream.endText();
	                contentStream.close();
	            }
	
	            //doc.save( arquivoSaida );
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            doc.save(bos);
	            arquivoSaida = bos.toByteArray();
	            
	            if( doc != null ) {
	                doc.close();
	            }	            
        	}
        
        } catch (IOException e) {
        	throw new PdfException(log, e.getMessage());        
        }
        return arquivoSaida;
    }

    public byte[] addImagemPDF(byte[] arquivoPDF, byte[] arquivoImagem,     		 
    		float posicaoX, float posicaoY) throws PdfException {

    	byte[] arquivoSaida = null;
    	PDDocument doc = null;
    	try {
    		
    		if (arquivoImagem!=null && arquivoImagem.length > 0) {
	            //doc = PDDocument.load( new File(arquivoPDF) );
    			doc = PDDocument.load(arquivoPDF);
    			
	            for( PDPage page : doc.getPages() ) {
	                PDRectangle pageSize = page.getMediaBox();
	                
	                float centerX = pageSize.getWidth();

	                File imgFile = new File("imagemTemp");
	                FileUtils.writeByteArrayToFile(imgFile, arquivoImagem);
	                
	            	PDImageXObject pdImage = PDImageXObject.createFromFileByContent(imgFile, doc);
	                PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true);

	                //posicaoX = (centerX - pdImage.getWidth())/2f;
	                //contentStream.drawImage(pdImage, posicaoX, posicaoY);
	                
	                // reduza esse valor se a imagem for muito grande
	                float scale = 0.5f;
	                posicaoX = (centerX - (pdImage.getWidth()*scale))/2f;
	                contentStream.drawImage(pdImage, posicaoX, posicaoY, pdImage.getWidth()*scale, pdImage.getHeight()*scale);

	                contentStream.close();
	            }
	            
	            //doc.save( arquivoSaida );
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            doc.save(bos);
	            arquivoSaida = bos.toByteArray();
	            
	            if( doc != null ) {
	                doc.close();
	            }
	    	}
	    } catch (IOException e) {
	    	throw new PdfException(log, e.getMessage());        
	    }
    	return arquivoSaida;
    }
    
	public PdfInfo getInfo(byte[] arquivoPDF) throws PdfException {
		PDDocument doc = null;
		PdfInfo pdfinfo = new PdfInfo();
		try {
			doc = PDDocument.load(arquivoPDF);

			PDDocumentInformation info = doc.getDocumentInformation();
			PDDocumentCatalog cat = doc.getDocumentCatalog();
			PDMetadata md = cat.getMetadata();
			AccessPermission ap = doc.getCurrentAccessPermission();
			
			pdfinfo.setNumeroPaginas(doc.getNumberOfPages());
			pdfinfo.setTitulo(info.getTitle());
			pdfinfo.setAutor(info.getAuthor());
			pdfinfo.setAssunto(info.getSubject());
			pdfinfo.setPalavrasChave(info.getKeywords());
			pdfinfo.setCriador(info.getCreator());
			pdfinfo.setProdutor(info.getProducer());
			pdfinfo.setDataCriacao(info.getCreationDate());
			pdfinfo.setDataModificacao(info.getModificationDate());
			pdfinfo.setPreso(info.getTrapped());
			
			if (md != null) {
				String metaData = new String(md.toByteArray(), "ISO-8859-1");
				pdfinfo.setMetadata(metaData);
			}

			pdfinfo.setPodeImprimir(ap.canPrint());
			pdfinfo.setPodeAgruparDocs(ap.canAssembleDocument());
			pdfinfo.setPodeCopiaConteudo(ap.canExtractContent());
			pdfinfo.setPodeCopiaConteudoAcessibilidade(ap.canExtractForAccessibility());
			pdfinfo.setPodeExtracaoPaginas(ap.canModify());
			pdfinfo.setPodeComentarios(ap.canModifyAnnotations());
			pdfinfo.setPodePreencherCamposForms(ap.canFillInForm());
			pdfinfo.setPodeImprimirDegrade(ap.canPrintDegraded());
			//pdfinfo.setPodeAssinatura(ap.);
			//pdfinfo.setPodeConverterTodosPontosEmProcesso(ap.);
			pdfinfo.setSomenteLeitura(ap.isReadOnly());
			//ap.isOwnerPermission()
									
			if (doc != null) {
				doc.close();
			}
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		}
		return pdfinfo;
	}

	public byte[] mudarPermissoes(byte[] arquivoPDF, 
			PdfPermissao permissao) throws PdfException {
		byte[] arquivoSaida = null;
		PDDocument doc = null;
		try {
			//doc = PDDocument.load(arquivoPDF, senhaPDF);
			doc = PDDocument.load(arquivoPDF);

			//PipedOutputStream os = new PipedOutputStream();
			// PipedInputStream is = new PipedInputStream(os);

			// para arquivos grandes
			// System.setProperty("org.apache.pdfbox.baseParser.pushBackSize",
			// "2024768");
			// InputStream dataStream = secureData.data();

			// PDDocument doc = PDDocument.load(dataStream, true);
			
			
	        if (doc.isEncrypted()) {
	           	//doc.decrypt("");
	            doc.setAllSecurityToBeRemoved(true);
	        }

			
			AccessPermission ap = new AccessPermission();
			ap.setCanPrint(permissao.isPodeImprimir());
			ap.setCanAssembleDocument(permissao.isPodeAgruparDocs());
			ap.setCanExtractContent(permissao.isPodeCopiaConteudo());
			ap.setCanExtractForAccessibility(permissao.isPodeCopiaConteudoAcessibilidade());
			ap.setCanModify(permissao.isPodeExtracaoPaginas());
			ap.setCanModifyAnnotations(permissao.isPodeComentarios());
			ap.setCanFillInForm(permissao.isPodePreencherCamposForms());
			ap.setCanPrintDegraded(permissao.isPodeImprimirDegrade());
			if (permissao.isSomenteLeitura()){
				ap.setReadOnly();
			}

			//UUID.randomUUID().toString()
			StandardProtectionPolicy spp = new StandardProtectionPolicy(
					permissao.getSenhaProprietario(), 
					permissao.getSenhaUsuario(), ap);
			
			doc.protect(spp);

			//doc.save(os);
			//doc.close();
			// dataStream.close();
			//os.close();
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
            doc.save(bos);
            arquivoSaida = bos.toByteArray();
            
            if( doc != null ) {
                doc.close();
            }    	
	    } catch (IOException e) {
	    	throw new PdfException(log, e.getMessage());        
	    }
		return arquivoSaida;	
	}

	public byte[] setPDFA(byte[] arquivoPDF, String nomePDF) throws PdfException {
		byte[] arquivoSaida = null;
		PDDocument doc = null;
		try {
			//doc = PDDocument.load(arquivoPDF, senhaPDF);
			doc = PDDocument.load(arquivoPDF);

            XMPMetadata xmp = XMPMetadata.createXMPMetadata();
            
			DublinCoreSchema dc = xmp.createAndAddDublinCoreSchema();
			dc.setTitle(nomePDF);
			
			PDFAIdentificationSchema id = xmp.createAndAddPFAIdentificationSchema();
			id.setPart(1);
			id.setConformance("B");
			
			XmpSerializer serializer = new XmpSerializer();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			serializer.serialize(xmp, baos, true);

			PDMetadata metadata = new PDMetadata(doc);
			metadata.importXMPMetadata(baos.toByteArray());
			doc.getDocumentCatalog().setMetadata(metadata);

            // sRGB output intent
            InputStream colorProfile = PdfUtil.class.getResourceAsStream(
                    "sRGB Color Space Profile.icm");
            PDOutputIntent intent = new PDOutputIntent(doc, colorProfile);
            intent.setInfo("sRGB IEC61966-2.1");
            intent.setOutputCondition("sRGB IEC61966-2.1");
            intent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
            intent.setRegistryName("http://www.color.org");
            doc.getDocumentCatalog().addOutputIntent(intent);
						
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
            doc.save(bos);
            arquivoSaida = bos.toByteArray();
            
            if( doc != null ) {
                doc.close();
            }    	
	    } catch (IOException e) {
	    	throw new PdfException(log, e.getMessage());        
	    } catch (BadFieldValueException e) {
	    	throw new PdfException(log, e.getMessage());
		} catch (TransformerException e) {
			throw new PdfException(log, e.getMessage());
		}
		return arquivoSaida;	
	}
		
	public Set<PdfInfoFonte> getInfoFonte(byte[] arquivoPDF) throws PdfException {
		PDDocument doc = null;
		Set<PdfInfoFonte> infoFonte = new LinkedHashSet<PdfInfoFonte>();
		try {
			//doc = PDDocument.load(arquivoPDF, senhaPDF);
			doc = PDDocument.load(arquivoPDF);

			for (PDPage pagina : doc.getPages()) {
				PDResources recursos = pagina.getResources();
				processaRecursos(recursos, infoFonte);
			}
		} catch (IOException e) {
			throw new PdfException(log, e.getMessage());
		} finally {
			if (doc != null) {
				try {
					doc.close();
				} catch (IOException e) {
					throw new PdfException(log, e.getMessage());
				}
			}
		}
		
		return infoFonte;
	}

	/*
	Tipo: TrueType
	Codificação: Ansi
	Fonte real: ArialMT
	Tipo de fonte real: TrueType
	*/	
	private void processaRecursos(PDResources recursos, Set<PdfInfoFonte> infoFonte) throws IOException {
		if (recursos == null) {
			return;
		}

		for (COSName cosNome : recursos.getFontNames()) {
			PDFont font = recursos.getFont(cosNome);

			if (font instanceof PDTrueTypeFont) {
				infoFonte.add(new PdfInfoFonte(font.getSubType(), font.getName()));
			} else if (font instanceof PDType0Font) {
				PDCIDFont descendantFont = ((PDType0Font) font).getDescendantFont();
				if (descendantFont instanceof PDCIDFontType2) {
					infoFonte.add(new PdfInfoFonte(font.getSubType(), font.getName()));	
				}
			}
		}

		for (COSName name : recursos.getXObjectNames()) {
			PDXObject xobject = recursos.getXObject(name);
			if (xobject instanceof PDFormXObject) {
				PDFormXObject xObjectForm = (PDFormXObject) xobject;
				PDResources formResources = xObjectForm.getResources();
				processaRecursos(formResources, infoFonte);
			}
		}
	}
	
}
