package br.hfs.util.assinatura;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JApplet;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AssinaturaApplet extends JApplet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(AssinaturaApplet.class.getName());

	@Override
	public void init() {
		try {
			/*
			 * for (UIManager.LookAndFeelInfo info :
			 * UIManager.getInstalledLookAndFeels()) { if
			 * ("Nimbus".equals(info.getName())) {
			 * UIManager.setLookAndFeel(info.getClassName()); break; } }
			 */
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
					getContentPane().add(new AssinaturaPanel(true, urlBase.toString()));						
				}
			});
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}


}
