package br.hfs.util.scanner;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class RotinasImagem {
	private static Logger log = Logger.getLogger("RotinasImagem");
	private static RotinasImagem instancia;

	public enum FormatoImagem {
		PNG, JPEG, GIF, BMP
	}

	private RotinasImagem() {
		super();
	}

	public static RotinasImagem getInstancia() {
		if (instancia == null) {
			instancia = new RotinasImagem();
		}
		return instancia;
	}
	
	public BufferedImage redimensionar(Image img, int largura, int altura) {
		BufferedImage bi = new BufferedImage(largura, altura,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2D = bi.createGraphics();

		Image scaledImage = img.getScaledInstance(largura, altura,
				Image.SCALE_AREA_AVERAGING);
		g2D.drawImage(scaledImage, 0, 0, largura, altura, null);
		g2D.dispose();
		return bi;
	}

	public BufferedImage getBufferedImage(Image img) {
		BufferedImage bi = new BufferedImage(img.getWidth(null), img
				.getHeight(null), BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();

		return bi;
	}

	public byte[] getBytes(Image img, FormatoImagem formato)
			throws ScannerException {
		try {
			BufferedImage bi = getBufferedImage(img);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			if (formato == FormatoImagem.GIF)
				ImageIO.write(bi, formato.name().toLowerCase(), buffer);
			else
				ImageIO.write(bi, formato.name().toUpperCase(), buffer);

			return buffer.toByteArray();
		} catch (IOException ex) {
			throw new ScannerException(log, ex.getMessage());
		}
	}

	public void salvar(Image img, String arquivoSemExtensao,
			FormatoImagem formato) throws ScannerException {
		BufferedImage bi = getBufferedImage(img);

		try {
			if (formato == FormatoImagem.GIF)
				ImageIO.write(bi, formato.name().toLowerCase(),
						new File(arquivoSemExtensao + "."
								+ formato.name().toLowerCase()));
			else
				ImageIO.write(bi, formato.name().toUpperCase(),
						new File(arquivoSemExtensao + "."
								+ formato.name().toLowerCase()));

		} catch (IOException e) {
			throw new ScannerException(log,
					"Não foi possível acessar o arquivo" + e.getMessage());
		}
	}

	public Image lerAssicrono(InputStream is, int tamanho)
			throws ScannerException {
		try {
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] byBuf = new byte[tamanho];
			int byteRead = bis.read(byBuf, 0, tamanho);
			if (byteRead > 0)
				return Toolkit.getDefaultToolkit().createImage(byBuf);
			else
				throw new ScannerException(log, "Stream vazio.");
		} catch (IOException e) {
			throw new ScannerException(log,
					"Não foi possível acessar o arquivo" + e.getMessage());
		}
	}

	public Image lerAssicrono(String arquivoComExtensao) {
		// Carga assícrona, carrega aos poucos
		return Toolkit.getDefaultToolkit().getImage(arquivoComExtensao);
	}

	public BufferedImage lerSicrono(String arquivoComExtensao)
			throws ScannerException {
		try {
			BufferedImage bi = ImageIO.read(new File(arquivoComExtensao));
			return bi;
		} catch (IOException e) {
			throw new ScannerException(log,
					"Não foi possível acessar o arquivo" + e.getMessage());
		}
	}

	public BufferedImage escalonar(Image img, double scalex, double scaley) {
		BufferedImage bi = getBufferedImage(img);

		AffineTransform tx = new AffineTransform();
		tx.scale(scalex, scaley);

		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		return op.filter(bi, null);
	}

	public BufferedImage podar(Image img, double shiftx, double shifty) {
		BufferedImage bi = getBufferedImage(img);

		AffineTransform tx = new AffineTransform();
		tx.shear(shiftx, shifty);

		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		return op.filter(bi, null);
	}

	public BufferedImage transferir(Image img, double x, double y) {
		BufferedImage bi = getBufferedImage(img);

		AffineTransform tx = new AffineTransform();
		tx.translate(x, y);

		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		return op.filter(bi, null);
	}

	public BufferedImage rotacionar(Image img, double radians, double x,
			double y) {
		BufferedImage bi = getBufferedImage(img);

		AffineTransform tx = new AffineTransform();
		tx.rotate(radians, bi.getWidth() / 2, bi.getHeight() / 2);

		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		return op.filter(bi, null);
	}

	public BufferedImage borrar(Image img, int largura, int altura) {
		BufferedImage bi = getBufferedImage(img);

		Kernel kernel = new Kernel(largura, altura, new float[] { 1f / 9f,
				1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f,
				1f / 9f });
		BufferedImageOp op = new ConvolveOp(kernel);
		return op.filter(bi, null);
	}

	public BufferedImage clarearEscurecer(Image img, float fatorEscala) {
		BufferedImage bi = getBufferedImage(img);

		// float scaleFactor = 1.3f; 30% (CLAREAR)
		// float scaleFactor = .9f; 10% (ESCURECER)
		RescaleOp op = new RescaleOp(fatorEscala, 0, null);
		return op.filter(bi, null);
	}

	public BufferedImage converteParaCinza(Image img) {
		BufferedImage bi = getBufferedImage(img);
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		return op.filter(bi, null);
	}

	public BufferedImage gravaEmRelevo(Image img, int largura, int altura) {
		BufferedImage bi = getBufferedImage(img);

		Kernel kernel = new Kernel(largura, altura, new float[] { -2, 0, 0, 0,
				1, 0, 0, 0, 2 });
		BufferedImageOp op = new ConvolveOp(kernel);
		return op.filter(bi, null);
	}

	public BufferedImage flipVerticalmente(Image img) {
		BufferedImage bi = getBufferedImage(img);

		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		tx.translate(0, -img.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		return op.filter(bi, null);
	}

	public BufferedImage flipHorizontalmente(Image img) {
		BufferedImage bi = getBufferedImage(img);

		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-img.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(bi, null);
	}

	public BufferedImage flipVerticalmenteHorizontalmente(Image img) {
		BufferedImage bi = getBufferedImage(img);

		// equivalent to rotating the image 180 degrees
		AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
		tx.translate(-img.getWidth(null), -img.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		return op.filter(bi, null);
	}

	public BufferedImage afilar(Image img, int largura, int altura) {
		BufferedImage bi = getBufferedImage(img);

		Kernel kernel = new Kernel(largura, altura, new float[] { -1, -1, -1,
				-1, 9, -1, -1, -1, -1 });
		BufferedImageOp op = new ConvolveOp(kernel);
		return op.filter(bi, null);
	}

}
