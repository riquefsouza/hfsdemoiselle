package br.hfs.util.scanner;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Logger;

public final class RotinasTransferencia implements ClipboardOwner {
	private static Logger log = Logger.getLogger("RotinasTransferencia");
	private static RotinasTransferencia instancia;

	private RotinasTransferencia() {
		super();
	}

	public static RotinasTransferencia getInstancia() {
		if (instancia == null) {
			instancia = new RotinasTransferencia();
		}
		return instancia;
	}

	public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
		//
	}

	public void setClipboardContents(String aString) {
		StringSelection stringSelection = new StringSelection(aString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);
	}

	public String getClipboardContents() throws ScannerException {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null)
				&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents
						.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException ex) {
				throw new ScannerException(log, ex.getMessage());
			} catch (IOException ex) {
				throw new ScannerException(log, ex.getMessage());
			}
		}
		return result;
	}

	public Image getImagemClipboard() throws ScannerException {
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
				.getContents(null);
		try {
			if (t != null && t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
				Image text = (Image) t.getTransferData(DataFlavor.imageFlavor);
				return text;
			}
		} catch (UnsupportedFlavorException ex) {
			throw new ScannerException(log, ex.getMessage());
		} catch (IOException ex) {
			throw new ScannerException(log, ex.getMessage());
		}
		return null;
	}

	public void setImagemClipboard(Image image) {
		ImagemTransferivel imgSel = new ImagemTransferivel(image);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel,
				null);
	}

}
