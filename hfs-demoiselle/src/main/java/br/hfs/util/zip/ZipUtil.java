package br.hfs.util.zip;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class ZipUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("ZipUtil");

	/**
	 * Criar arquivo zipado
	 * 
	 * @param nomeArquivoZip
	 *            nome do arquivo a ser zipado
	 * @param arquivos
	 *            arquivos a serem zipados
	 */
	public void criarZIP(String nomeArquivoZip, String dirArquivos,
			List<String> arquivos) throws ZipException {
		// Cria um buffer para a leitura dos arquivos
		byte[] buf = new byte[1024];

		try {
			// Cria o arquivo ZIP
			String outFilename = nomeArquivoZip;
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					outFilename));

			// Comprime os arquivos
			for (String arq : arquivos) {
				FileInputStream in = new FileInputStream(dirArquivos + "/"
						+ arq);

				// Adiciona uma entrada ZIP para a stream de saída.
				out.putNextEntry(new ZipEntry(arq));

				// Transfere bytes do arquivo para o arquivo ZIP
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				// Fecha a entrada
				out.flush();
				out.closeEntry();
				in.close();
			}

			// Fecha o arquivo ZIP
			out.close();
		} catch (IOException e) {
			throw new ZipException(log,
					"Erro de entrada/saída ao criar arquivo ZIP, "
							+ e.getMessage());
		}
	}

	public void criarZIP(String nomeArquivoZip, String dirArquivo,
			List<String> dirArquivos, List<String> arquivos)
			throws ZipException {
		// Cria um buffer para a leitura dos arquivos
		byte[] buf = new byte[1024];
		FileInputStream in;

		try {
			// Cria o arquivo ZIP
			String outFilename = nomeArquivoZip;
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					outFilename));

			// Comprime os arquivos
			for (int i = 0; i < arquivos.size(); i++) {
				if (dirArquivos.get(i).trim().length() == 0) {
					in = new FileInputStream(dirArquivo + "/"
							+ dirArquivos.get(i) + "/" + arquivos.get(i));

					// Adiciona uma entrada ZIP para a stream de saída.
					out.putNextEntry(new ZipEntry(arquivos.get(i)));
				} else {
					in = new FileInputStream(dirArquivo + "/"
							+ dirArquivos.get(i) + "/" + arquivos.get(i));

					// Adiciona uma entrada ZIP para a stream de saída.
					out.putNextEntry(new ZipEntry(dirArquivos.get(i) + "/"
							+ arquivos.get(i)));
				}
				// Transfere bytes do arquivo para o arquivo ZIP
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();

				// Fecha a entrada
				out.closeEntry();
			}

			// Fecha o arquivo ZIP
			out.close();
		} catch (IOException e) {
			throw new ZipException(log,
					"Erro de entrada/saída ao criar arquivo ZIP, "
							+ e.getMessage());
		}
	}

	/**
	 * Listar arquivos num arquivo zipado
	 * 
	 * @param nomeArquivoZip
	 *            nome do arquivo zipado
	 * @return o nome dos arquivos zipados
	 */
	public List<String> listarZIP(String nomeArquivoZip) throws ZipException {
		List<String> lista = new ArrayList<String>();
		try {
			ZipFile zf = new ZipFile(nomeArquivoZip);

			for (Enumeration<? extends ZipEntry> entries = zf.entries(); entries
					.hasMoreElements();) {
				String zipEntryName = entries.nextElement().getName();
				lista.add(zipEntryName);
			}

			zf.close();
		} catch (IOException e) {
			throw new ZipException(log,
					"Erro de entrada/saída ao listar arquivo ZIP, "
							+ e.getMessage());
		}
		return lista;
	}

	/**
	 * Recupera os arquivos num arquivo zipado
	 * 
	 * @param nomeArquivoZip
	 *            nome do arquivo zipado
	 */
	public void recupeparZIP(String nomeArquivoZip, String dirArquivo)
			throws ZipException {
		try {
			ZipInputStream in = new ZipInputStream(new FileInputStream(
					nomeArquivoZip));

			ZipEntry entry = in.getNextEntry();
			do {
				String outFilename = entry.getName();
				OutputStream out = new FileOutputStream(dirArquivo
						+ outFilename);

				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				out.close();

				entry = in.getNextEntry();

			} while (entry != null);

			in.close();
		} catch (IOException e) {
			throw new ZipException(log,
					"Erro de entrada/saída ao recupepar arquivos ZIP, "
							+ e.getMessage());
		}
	}

	/**
	 * Calcula o Checksum CRC32 de um arquivo
	 * 
	 * @param arquivoNome
	 *            o nomes do arquivo
	 * @return o Checksum CRC32 de um arquivo
	 */
	public long calculaCRC32Arquivo(String arquivoNome) throws ZipException {
		long checksum = -1;
		try {
			// Computa CRC32 checksum
			CheckedInputStream cis = new CheckedInputStream(
					new FileInputStream(arquivoNome), new CRC32());
			byte[] tempBuf = new byte[128];
			while (cis.read(tempBuf) >= 0) {
			}
			checksum = cis.getChecksum().getValue();
			cis.close();
		} catch (IOException e) {
			throw new ZipException(log,
					"Erro de entrada/saída ao calcular o CRC32, "
							+ e.getMessage());
		}
		return checksum;
	}

	/**
	 * Calcula o Checksum CRC32 de um array de bytes
	 * 
	 * @param bytes
	 *            o array de bytes
	 * @return o Checksum CRC32 do array de bytes
	 */
	public long calculaCRC32(byte[] bytes) throws ZipException {
		// Computa CRC-32 checksum
		Checksum checksumEngine = new CRC32();
		checksumEngine.update(bytes, 0, bytes.length);
		return checksumEngine.getValue();
	}

	/**
	 * Calcula o Checksum CRC32 de uma string
	 * 
	 * @param str
	 *            a string
	 * @return o Checksum CRC32 da string
	 */
	public long calculaCRC32(String str) throws ZipException {
		return calculaCRC32(str.getBytes());
	}

	public byte[] comprimirByteArray(byte[] input) throws ZipException {
		byte[] compressedData = null;

		try {
			Deflater compressor = new Deflater();
			compressor.setLevel(Deflater.BEST_COMPRESSION);

			compressor.setInput(input);
			compressor.finish();

			ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

			byte[] buf = new byte[1024];
			while (!compressor.finished()) {
				int count = compressor.deflate(buf);
				bos.write(buf, 0, count);
			}
			bos.flush();
			bos.close();

			compressedData = bos.toByteArray();
		} catch (IOException e) {
			throw new ZipException(log, e.getMessage());
		}

		return compressedData;
	}

	public byte[] descomprimirByteArray(byte[] compressedData)
			throws ZipException {
		byte[] decompressedData = null;

		try {
			Inflater decompressor = new Inflater();
			decompressor.setInput(compressedData);

			ByteArrayOutputStream bos = new ByteArrayOutputStream(
					compressedData.length);

			byte[] buf = new byte[1024];
			while (!decompressor.finished()) {
				int count = decompressor.inflate(buf);
				bos.write(buf, 0, count);
			}
			bos.flush();
			bos.close();

			decompressedData = bos.toByteArray();
		} catch (DataFormatException e) {
			throw new ZipException(log, e.getMessage());
		} catch (IOException e) {
			throw new ZipException(log, e.getMessage());
		}

		return decompressedData;
	}

}