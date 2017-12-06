package br.hfs.util.scanner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

public class ScannerImagem extends Component {
	private static final long serialVersionUID = 7612380200304871523L;
	private Image image;

	public ScannerImagem(Image image) {
		this.image = image;

		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(this.image, 1);

		try {
			tracker.waitForAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Dimension getPreferredSize() {
		if (image == null || image.getHeight(null) <= 0)
			return new Dimension(300, 300);
		else
			return new Dimension(image.getWidth(null), image.getHeight(null));
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		if (image != null && image.getHeight(null) > 0) {
			g.drawImage(image, 0, 0, this);
		} else {
			g.drawString("Desculpe não foi possível carregar a imagem!", 20, 20);
		}
	}
}
