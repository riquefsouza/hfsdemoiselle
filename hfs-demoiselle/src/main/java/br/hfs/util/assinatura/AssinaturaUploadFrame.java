package br.hfs.util.assinatura;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AssinaturaUploadFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(AssinaturaUploadFrame.class.getName());
	
	public AssinaturaUploadFrame(String urlBase) {
		super("Assinador Digital");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		AssinaturaUploadPanel assinaturaUploadPanel = new AssinaturaUploadPanel(urlBase);
		assinaturaUploadPanel.setOpaque(true);
		setContentPane(assinaturaUploadPanel);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		final String urlBase = args[0];
		
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
				
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				if (urlBase!=null){
					if (!urlBase.isEmpty()){
						logger.log(Level.INFO,"URLBase: "+ urlBase);
						
						new AssinaturaUploadFrame(urlBase);
					}
				}
			}
		});
	}
	
}
