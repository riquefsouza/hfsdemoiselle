package br.hfs.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;

public class Captcha {

	private static final int TAMANHO_FONTE = 26;
	private static final String NOME_FONTE = "Verdana";
	public static final String EXTENSAO_IMAGEM = "image/png";

	// private static final Color COR = new
	// Color(0.6662f, 0.4569f, 0.3232f);
	private static final Color COR = new Color(255, 255, 255);
	private static final GradientPaint GRADIENT_PAINT = new GradientPaint(30, 30, COR, 15, 25, Color.white, true);
	private static final Font FONTE_CAPTCHA = new Font(NOME_FONTE, Font.CENTER_BASELINE, TAMANHO_FONTE);

	private int height;
	private int width;

	public Captcha(int height, int width) {
		this.setHeight(height);
		this.setWidth(width);
	}

	public BufferedImage gerarImg(String codigo) throws IOException, ServletException {

		BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = image.createGraphics();
		graphics2D.setPaint(GRADIENT_PAINT);
		graphics2D.setFont(FONTE_CAPTCHA);

		for (int i = 0; i <= 6; i++) {
			int aux = (i * 20) + 5;
			int auxNivel = 0;
			if (i == 0) {
				graphics2D.drawString(codigo.substring(i == 0 ? i : i - 1, i), aux, 20);
			} else if (i > 2) {
				auxNivel = (i - 2) * 5;
				graphics2D.drawString(codigo.substring(i - 1, i), aux, auxNivel + 15);
			} else {
				auxNivel = i * 5;
				graphics2D.drawString(codigo.substring(i - 1, i), aux, auxNivel + 25);
			}
		}

		graphics2D.dispose();

		return image;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
