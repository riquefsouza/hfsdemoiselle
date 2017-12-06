package br.hfs.util.certificado;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public final class CertificadoUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("CertificadoUtil");

	public void addToKeyStore(File keystoreFile, char[] keystorePassword,
			String alias, Certificate cert) throws CertificadoException {
		try {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());

			FileInputStream in = new FileInputStream(keystoreFile);
			keystore.load(in, keystorePassword);
			in.close();

			keystore.setCertificateEntry(alias, cert);

			FileOutputStream out = new FileOutputStream(keystoreFile);
			keystore.store(out, keystorePassword);
			out.close();
		} catch (java.security.cert.CertificateException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (FileNotFoundException e) {
			// Keystore does not exist
			throw new CertificadoException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (IOException e) {
			throw new CertificadoException(log, e.getMessage());
		}
	}

	public void listarAliasesKeyStore(File arquivo, String senha) throws CertificadoException {
		try {
			FileInputStream is = new FileInputStream(arquivo);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(is, senha.toCharArray());

			Enumeration<String> enum1 = keystore.aliases();
			while (enum1.hasMoreElements()) {
				String alias = enum1.nextElement();
				//boolean b = keystore.isKeyEntry(alias);
				//b = keystore.isCertificateEntry(alias);
				System.out.println(alias);
			}		
			is.close();
		} catch (CertificateException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (FileNotFoundException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (IOException e) {
			throw new CertificadoException(log, e.getMessage());
		}
	}

	public Certificate getCertificadoDoKeyStore(File arquivo, String senha, String alias) throws CertificadoException {
		try {
			FileInputStream is = new FileInputStream(arquivo);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(is, senha.toCharArray());

			Certificate cert = keystore.getCertificate(alias);
			return cert;
		} catch (KeyStoreException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (java.security.cert.CertificateException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (java.io.IOException e) {
			throw new CertificadoException(log, e.getMessage());
		}
	}

	public KeyPair getKeyPair(KeyStore keystore, String alias,
			char[] password) throws CertificadoException {
		try {

			Key key = keystore.getKey(alias, password);
			if (key instanceof PrivateKey) {
				Certificate cert = keystore.getCertificate(alias);
				PublicKey publicKey = cert.getPublicKey();
				return new KeyPair(publicKey, (PrivateKey) key);
			}
		} catch (UnrecoverableKeyException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new CertificadoException(log, e.getMessage());
		}
		return null;
	}
	
	public CertificadoX509 getCertificadoX509(byte[] arquivo) throws CertificadoException {
		CertificadoX509 cx = new CertificadoX509();
		try {
			//FileInputStream is = new FileInputStream(arquivo);
			ByteArrayInputStream bai = new ByteArrayInputStream(arquivo);
			
		    CertificateFactory cf = CertificateFactory.getInstance("X.509");
		    Certificate cert = cf.generateCertificate(bai);
		    
		    if (cert instanceof X509Certificate) {
		        X509Certificate x509cert = (X509Certificate) cert;
		        cx = x509CertToCertificadoX509(x509cert);
		    }

		    //is.close();
		    bai.close();
		} catch (FileNotFoundException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (IOException e) {
			throw new CertificadoException(log, e.getMessage());
		}
		
		return cx;
	}

	private String encodeHexString(byte[] b) {
		String hexDigits = "0123456789abcdef";
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			int j = ((int) b[i]) & 0xFF;
			buf.append(hexDigits.charAt(j / 16));
			buf.append(hexDigits.charAt(j % 16));
		}

		return buf.toString().toUpperCase();
	}
	
	private CertificadoX509 x509CertToCertificadoX509(
			X509Certificate x509cert) throws CertificateParsingException {
		CertificadoX509 cx = new CertificadoX509();
		
		cx.setType(x509cert.getType());
		cx.setVersion(x509cert.getVersion());
		cx.setSubjectDN(x509cert.getSubjectDN().getName());
		cx.setSignatureAlgorithm(x509cert.getSigAlgName());
		cx.setPublicKeyAlgorithm(x509cert.getPublicKey().getAlgorithm());
		cx.setPublicKeyFormato(x509cert.getPublicKey().getFormat());
		cx.setPublicKey(encodeHexString(x509cert.getPublicKey().getEncoded()));
		cx.setValidityNotBefore(x509cert.getNotBefore());
		cx.setValidityNotAfter(x509cert.getNotAfter());
		cx.setIssuerDN(x509cert.getIssuerDN().getName());
		cx.setSerialNumber(x509cert.getSerialNumber().toString());
		
		if (x509cert.getSubjectAlternativeNames()!=null) {
			for (List<?> alter : x509cert.getSubjectAlternativeNames()) {
				for (int i = 0; i < alter.size(); i++) {
					Object item = alter.get(i);
	
					if (i==1){
						cx.setEmail(item.toString());
						break;
					}
				}
				break;
			}
		}
        /*
        byte[] encodedExtensionValue = x509cert.getExtensionValue("1.3.6.1.5.5.7.1.1");
        if (encodedExtensionValue != null) {
            ASN1Primitive extensionValue = JcaX509ExtensionUtils
                    .parseExtensionValue(encodedExtensionValue);		            
            String values = extensionValue.toString();
            System.out.println(values);
        } 
        
	    Set<String> critSet = x509cert.getNonCriticalExtensionOIDs();
	    if (critSet != null && !critSet.isEmpty()) {
	        for (String oid : critSet) {
	        	System.out.println(oid);
	        }
	    }
			
		*/
		
		return cx;
	}
	
	public void getCertificadosP7B(File arquivo) throws CertificadoException {
		try {
			 FileInputStream fis = new FileInputStream(arquivo);
			 CertificateFactory cf = CertificateFactory.getInstance("X.509");
			 Collection<? extends Certificate> c = cf.generateCertificates(fis);
			 Iterator<? extends Certificate> i = c.iterator();
			 while (i.hasNext()) {
			    Certificate cert = (Certificate)i.next();
			    System.out.println(cert);
			 }
			 fis.close();
		} catch (FileNotFoundException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (IOException e) {
			throw new CertificadoException(log, e.getMessage());
		}
	}
	
	public List<String> listarAliasTokenSmart() throws CertificadoException {
		List<String> lista = new ArrayList<String>();
		if ("Linux".compareTo(System.getProperty("os.name")) != 0) {
			preparaListaAliasWindows(lista);
		} else {			
			preparaListaAliasLinux(lista);
		}
		return lista;
	}

	private void preparaListaAliasWindows(List<String> lista) throws CertificadoException {
		try {
			KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			ks.load(null, null);
			Enumeration<?> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) {
				String aliasKey = (String) aliasEnum.nextElement();
				String type = ks.getCertificate(aliasKey).getPublicKey()
						.getFormat();

				if ((type.equalsIgnoreCase("X.509"))
						&& (ks.isKeyEntry(aliasKey)))
					lista.add(aliasKey);
			}
		} catch (KeyStoreException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchProviderException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (IOException e) {
			throw new CertificadoException(log, e.getMessage());
		}
	}

	private void preparaListaAliasLinux(List<String> lista) throws CertificadoException {
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null, null);
			Enumeration<?> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) {
				String aliasKey = (String) aliasEnum.nextElement();
				String type = ks.getCertificate(aliasKey).getPublicKey()
						.getFormat();

				if ((type.equalsIgnoreCase("X.509"))
						&& (ks.isKeyEntry(aliasKey)))
					lista.add(aliasKey);
			}
		} catch (KeyStoreException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (IOException e) {
			throw new CertificadoException(log, e.getMessage());
		}
	}
	
	public X509Certificate getCertificatePorAliasWindows(String alias) throws CertificadoException {
		try {
			KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			ks.load(null, null);
			Enumeration<?> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) {
				String aliasKey = (String) aliasEnum.nextElement();
				String type = ks.getCertificate(aliasKey).getPublicKey()
						.getFormat();
				if ((type.equalsIgnoreCase("X.509"))
						&& (ks.isKeyEntry(aliasKey))
						&& (aliasKey.equals(alias)))
					return (X509Certificate) ks.getCertificate(aliasKey);
			}
		} catch (KeyStoreException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchProviderException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (IOException e) {
			throw new CertificadoException(log, e.getMessage());
		}
		return null;
	}
	
	public CertificadoX509 getCertificadoPorAliasWindows(String alias) throws CertificadoException {
		CertificadoX509 cx = new CertificadoX509();
		
		try {
			X509Certificate xc = getCertificatePorAliasWindows(alias); 
			if (xc==null){
				throw new CertificadoException(log, "Certificado não encontrado!");
			} else {			
				cx = x509CertToCertificadoX509(xc);
			}
		} catch (CertificateParsingException e) {
			throw new CertificadoException(log, e.getMessage());
		}
		
		return cx;
	}
	
	public X509Certificate getCertificateTokenWindows() throws CertificadoException {
		try {
			KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			ks.load(null, null);
			Enumeration<?> aliasEnum = ks.aliases();
			while (aliasEnum.hasMoreElements()) {
				String aliasKey = (String) aliasEnum.nextElement();
				String type = ks.getCertificate(aliasKey).getPublicKey()
						.getFormat();
				if ((type.equalsIgnoreCase("X.509"))
						&& (ks.isKeyEntry(aliasKey)))						
					return (X509Certificate) ks.getCertificate(aliasKey);
			}
		} catch (KeyStoreException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchProviderException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (CertificateException e) {
			throw new CertificadoException(log, e.getMessage());
		} catch (IOException e) {
			throw new CertificadoException(log, e.getMessage());
		}
		return null;
	}
	
	public CertificadoX509 getCertificadoTokenWindows() throws CertificadoException {
		CertificadoX509 cx = new CertificadoX509();
		
		try {
			X509Certificate xc = getCertificateTokenWindows(); 
			if (xc==null){
				throw new CertificadoException(log, "Certificado não encontrado!");
			} else {			
				cx = x509CertToCertificadoX509(xc);
			}
		} catch (CertificateParsingException e) {
			throw new CertificadoException(log, e.getMessage());
		}
		
		return cx;
	}
	
}
