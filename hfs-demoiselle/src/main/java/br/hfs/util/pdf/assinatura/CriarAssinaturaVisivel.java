/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.hfs.util.pdf.assinatura;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Enumeration;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSignDesigner;

public class CriarAssinaturaVisivel extends CriarAssinaturaBase {
	//public static final BouncyCastleProvider BCPROVIDER = new BouncyCastleProvider();

	private SignatureOptions signatureOptions;
	private PDVisibleSignDesigner visibleSignDesigner;
	private final PDVisibleSigProperties visibleSignatureProperties = new PDVisibleSigProperties();

	public void setvisibleSignDesigner(InputStream filename, int x, int y,
			int zoomPercent, InputStream image, int page)
			throws IOException {
		visibleSignDesigner = new PDVisibleSignDesigner(filename, image, page);
		visibleSignDesigner.xAxis(x).yAxis(y).zoom(zoomPercent)
				.signatureFieldName("signature");
	}

	public void setVisibleSignatureProperties(String name, String location,
			String reason, int preferredSize, int page,
			boolean visualSignEnabled) throws IOException {
		visibleSignatureProperties.signerName(name).signerLocation(location)
				.signatureReason(reason).preferredSize(preferredSize)
				.page(page).visualSignEnabled(visualSignEnabled)
				.setPdVisibleSignature(visibleSignDesigner).buildSignature();
	}

	/**
	 * Initialize the signature creator with a keystore (pkcs12) and pin that
	 * should be used for the signature.
	 *
	 * @param keystore
	 *            is a pkcs12 keystore.
	 * @param pin
	 *            is the pin for the keystore / private key
	 * @throws KeyStoreException
	 *             if the keystore has not been initialized (loaded)
	 * @throws NoSuchAlgorithmException
	 *             if the algorithm for recovering the key cannot be found
	 * @throws UnrecoverableKeyException
	 *             if the given password is wrong
	 * @throws CertificateException
	 *             if the certificate is not valid as signing time
	 */
	public CriarAssinaturaVisivel(KeyStore keystore, char[] pin)
			throws KeyStoreException, UnrecoverableKeyException,
			NoSuchAlgorithmException, IOException, CertificateException {
		// grabs the first alias from the keystore and get the private key. An
		// alternative method or constructor could be used for setting a
		// specific
		// alias that should be used.
		Enumeration<String> aliases = keystore.aliases();
		String alias = "";
		while (aliases.hasMoreElements()) {
			alias = aliases.nextElement();
			if (keystore.isKeyEntry(alias)) {
				break;
			}
		}
		if (alias.isEmpty()){
			throw new IOException("Certificado Digital não existe!");
		}			
			
		setPrivateKey((PrivateKey) keystore.getKey(alias, pin));
		Certificate cert = keystore.getCertificateChain(alias)[0];
		setCertificate(cert);
		if (cert instanceof X509Certificate) {
			// avoid expired certificate
			((X509Certificate) cert).checkValidity();
		}
	}

	public CriarAssinaturaVisivel(KeyStore keystore)
			throws KeyStoreException, UnrecoverableKeyException,
			NoSuchAlgorithmException, IOException, CertificateException {
		
		Enumeration<String> aliases = keystore.aliases();
		String alias = "";
		while (aliases.hasMoreElements()) {
			alias = aliases.nextElement();
			if (keystore.isKeyEntry(alias)) {
				break;
			}
		}
		if (alias.isEmpty()){
			throw new IOException("Certificado Digital não existe!");
		}			
			
		setPrivateKey((PrivateKey) keystore.getKey(alias, null));
		Certificate cert = keystore.getCertificate(alias);
		setCertificate(cert);
		if (cert instanceof X509Certificate) {
			// avoid expired certificate
			((X509Certificate) cert).checkValidity();
		}
	}
	
	/**
	 * Sign pdf file and create new file that ends with "_signed.pdf".
	 *
	 * @param inputFile
	 *            The source pdf document file.
	 * @param signedFile
	 *            The file to be signed.
	 * @param tsaClient
	 *            optional TSA client
	 * @throws IOException
	 */
	public void signPDF(InputStream inputFile, OutputStream fos, TSAClient tsaClient)
			throws IOException {
		setTsaClient(tsaClient);

		if (inputFile == null) {
			throw new IOException("Document for signing does not exist");
		}

		// creating output document and prepare the IO streams.
		//FileOutputStream fos = new FileOutputStream(signedFile);

		// load document
		PDDocument doc = PDDocument.load(inputFile);

		// create signature dictionary
		PDSignature signature = new PDSignature();

		// default filter
		signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);

		// subfilter for basic and PAdES Part 2 signatures
		signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);

		if (visibleSignatureProperties != null) {
			signature.setName(visibleSignatureProperties.getSignerName());
			signature.setLocation(visibleSignatureProperties
					.getSignerLocation());
			signature
					.setReason(visibleSignatureProperties.getSignatureReason());
		}

		// the signing date, needed for valid signature
		signature.setSignDate(Calendar.getInstance());

		// register signature dictionary and sign interface
		if (visibleSignatureProperties != null
				&& visibleSignatureProperties.isVisualSignEnabled()) {
			signatureOptions = new SignatureOptions();
			signatureOptions.setVisualSignature(visibleSignatureProperties
					.getVisibleSignature());
			signatureOptions.setPage(visibleSignatureProperties.getPage() - 1);
			doc.addSignature(signature, this, signatureOptions);
		} else {
			doc.addSignature(signature, this);
		}

		// write incremental (only for signing purpose)
		doc.saveIncremental(fos);
		doc.close();

		// do not close options before saving, because some COSStream objects
		// within options
		// are transferred to the signed document.
		IOUtils.closeQuietly(signatureOptions);
	}

}
