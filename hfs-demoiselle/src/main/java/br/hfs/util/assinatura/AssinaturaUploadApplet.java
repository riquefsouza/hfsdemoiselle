package br.hfs.util.assinatura;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AssinaturaUploadApplet extends JApplet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(AssinaturaUploadApplet.class.getName());

	@Override
	public void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			logger.log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			logger.log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			logger.log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			logger.log(Level.SEVERE, null, ex);
		}

		try {
			java.awt.EventQueue.invokeAndWait(new Runnable() {
				public void run() {					
					URL urlBase = getCodeBase();					
					getContentPane().add(new AssinaturaUploadPanel(urlBase.toString()));						
				}
			});
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}


}
