package br.hfs.util.security;

import java.security.PrivateKey;
import java.security.cert.Certificate;

import br.gov.frameworkdemoiselle.certificate.exception.CertificateCoreException;
import br.gov.frameworkdemoiselle.certificate.timestamp.TimeStampGenerator;
import br.gov.frameworkdemoiselle.timestamp.connector.TimeStampOperator;

public class TimestampGeneratorImpl implements TimeStampGenerator {

//	private static final Logger logger = LoggerFactory
//			.getLogger(TimestampGeneratorImpl.class);
	private byte[] content;
	private PrivateKey privateKey;
	private Certificate[] certificates;

	/**
	 * 
	 * Inicializa os atributos necessarios para obter o carimbo de tempo
	 * 
	 *
	 * 
	 * @param content
	 * 
	 * @param privateKey
	 * 
	 * @param certificates
	 * 
	 * @throws CertificateCoreException
	 */

	@Override
	public void initialize(byte[] content, PrivateKey privateKey,
			Certificate[] certificates) throws CertificateCoreException {
		this.content = content;
		this.privateKey = privateKey;
		this.certificates = certificates;
	}

	/**
	 * 
	 * Envia a requisicao de carimbo de tempo para um servidor de carimbo de
	 * 
	 * tempo
	 * 
	 *
	 * 
	 * @return O carimbo de tempo retornado pelo servidor
	 */

	@Override
	public byte[] generateTimeStamp() throws CertificateCoreException {
		TimeStampOperator timeStampOperator = new TimeStampOperator();
		byte[] request = timeStampOperator.createRequest(privateKey,
				certificates, content);
		return timeStampOperator.invoke(request);
	}

	/**
	 * 
	 * Valida um carimnbo de tempo e o documento original
	 * 
	 *
	 * 
	 * @param content
	 *            o conteudo original
	 * 
	 * @param response
	 *            O carimbo de tempo a ser validado
	 * 
	 *
	 */

	@Override
	public void validateTimeStamp(byte[] content, byte[] response)
			throws CertificateCoreException {
		TimeStampOperator timeStampOperator = new TimeStampOperator();
		timeStampOperator.validate(content, response);
	}

}