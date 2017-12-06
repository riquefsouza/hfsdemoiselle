package br.hfs.util.assinatura;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AssinaturaFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(AssinaturaFrame.class.getName());
	
	public AssinaturaFrame(String urlBase) {
		super("Assinador Digital");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		AssinaturaPanel assinaturaPanel = new AssinaturaPanel(true, urlBase);
		assinaturaPanel.setOpaque(true);
		setContentPane(assinaturaPanel);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		final String urlBase = args[0];
		
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
				
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				if (urlBase!=null){
					if (!urlBase.isEmpty()){
						logger.log(Level.INFO,"URLBase: "+ urlBase);
						
						new AssinaturaFrame(urlBase);
					}
				}
			}
		});
	}
	
}
