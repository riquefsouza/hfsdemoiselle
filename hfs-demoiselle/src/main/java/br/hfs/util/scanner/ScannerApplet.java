package br.hfs.util.scanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.commons.codec.binary.Base64;

public final class ScannerApplet extends JApplet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger("ScannerApplet");

	private JPanel jContentPane = null;
	private JPanel panelSul = null;
	private JLabel labStatus = null;
	private ScrollPane jScrollPane = null;
	private JPanel panelBotoes = null;
	private JButton btnResetar = null;
	private JButton btnAdaptar = null;
	private JButton btnAumentarZoom = null;
	private JButton btnDiminuirZoom = null;
	private JPanel panelScannear = null;
	private JButton btnScan = null;
	private JButton btnLimpar = null;
	private JPanel panelStatus = null;

	private ScannerImagem simagem;
	private Image imagem;
	private int imgLargura;
	private int imgAltura;
	private static final int ZOOM = 100;
	private static final int MAX_ZOOM = 1500;

	private String caminhoImagens = "/br/jus/trt1/hfs-demoiselle/util/scanner/imagens/";

	/**
	 * This is the xxx default constructor
	 */
	public ScannerApplet() {
		super();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() {
		super.init();

		String caminho = this.getParameter("caminhodll");
		ScannerUtil.getInstancia().lerBiblioteca(caminho);

		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
	}

	public void start() {
		super.start();
	}

	public void stop() {
		super.stop();
	}

	public void destroy() {
		super.destroy();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPanelSul(), BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getPanelBotoes(), BorderLayout.EAST);
		}
		return jContentPane;
	}

	/**
	 * This method initializes panelSul
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelSul() {
		if (panelSul == null) {
			labStatus = new JLabel();
			setTextoStatus(0, 0);
			panelSul = new JPanel();
			panelSul.setLayout(new BorderLayout());
			panelSul.add(getPanelScannear(), BorderLayout.NORTH);
			panelSul.add(getPanelStatus(), BorderLayout.CENTER);
		}
		return panelSul;
	}

	/**
	 * This method initializes panelStatus
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelStatus() {
		if (panelStatus == null) {
			panelStatus = new JPanel();
			panelStatus.setLayout(new BorderLayout());
			panelStatus.setBorder(BorderFactory
					.createBevelBorder(BevelBorder.LOWERED));
			panelStatus.add(labStatus, BorderLayout.CENTER);
		}
		return panelStatus;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private ScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
			jScrollPane.setBackground(Color.lightGray);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes panelBotoes
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelBotoes() {
		if (panelBotoes == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(4);
			flowLayout.setVgap(4);
			panelBotoes = new JPanel();
			panelBotoes
					.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			panelBotoes.setLayout(flowLayout);
			panelBotoes.setPreferredSize(new Dimension(110, 36));
			panelBotoes.add(getBtnResetar(), null);
			panelBotoes.add(getBtnAdaptar(), null);
			panelBotoes.add(getBtnAumentarZoom(), null);
			panelBotoes.add(getBtnDiminuirZoom(), null);
		}
		return panelBotoes;
	}

	/**
	 * This method initializes btnResetar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnResetar() {
		if (btnResetar == null) {
			btnResetar = new JButton();
			btnResetar.setText("Resetar");
			btnResetar.setToolTipText("Resetar imagem");
			btnResetar.setName("btnResetar");
			btnResetar.setIcon(new ImageIcon(getClass().getResource(
					caminhoImagens + "HFSScanner-Resetar.png")));
			btnResetar.setPreferredSize(new Dimension(100, 26));
			btnResetar.setEnabled(false);
			btnResetar.setMnemonic(KeyEvent.VK_R);
			btnResetar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnResetarClick();
				}
			});
		}
		return btnResetar;
	}

	/**
	 * This method initializes btnAdaptar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdaptar() {
		if (btnAdaptar == null) {
			btnAdaptar = new JButton();
			btnAdaptar.setText("Adaptar");
			btnAdaptar.setName("btnAdaptar");
			btnAdaptar.setIcon(new ImageIcon(getClass().getResource(
					caminhoImagens + "HFSScanner-Adaptar.png")));
			btnAdaptar.setPreferredSize(new Dimension(100, 26));
			btnAdaptar.setEnabled(false);
			btnAdaptar.setMnemonic(KeyEvent.VK_A);
			btnAdaptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnAdaptarClick();
				}
			});
		}
		return btnAdaptar;
	}

	/**
	 * This method initializes btnAumentarZoom
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAumentarZoom() {
		if (btnAumentarZoom == null) {
			btnAumentarZoom = new JButton();
			btnAumentarZoom.setText("+ Zoom");
			btnAumentarZoom.setIcon(new ImageIcon(getClass().getResource(
					caminhoImagens + "HFSScanner-AumentarZoom.png")));
			btnAumentarZoom.setPreferredSize(new Dimension(100, 26));
			btnAumentarZoom.setEnabled(false);
			btnAumentarZoom.setActionCommand("AumentarZoom");
			btnAumentarZoom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							btnAumentarZoomClick();
						}
					});
		}
		return btnAumentarZoom;
	}

	/**
	 * This method initializes btnDiminuirZoom
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDiminuirZoom() {
		if (btnDiminuirZoom == null) {
			btnDiminuirZoom = new JButton();
			btnDiminuirZoom.setText("- Zoom");
			btnDiminuirZoom.setIcon(new ImageIcon(getClass().getResource(
					caminhoImagens + "HFSScanner-DiminuirZoom.png")));
			btnDiminuirZoom.setPreferredSize(new Dimension(100, 26));
			btnDiminuirZoom.setEnabled(false);
			btnDiminuirZoom.setActionCommand("DiminuirZoom");
			btnDiminuirZoom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							btnDiminuirZoomClick();
						}
					});
		}
		return btnDiminuirZoom;
	}

	/**
	 * This method initializes panelScannear
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelScannear() {
		if (panelScannear == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setHgap(4);
			flowLayout1.setVgap(4);
			panelScannear = new JPanel();
			panelScannear.setBorder(BorderFactory.createLineBorder(Color.gray,
					1));
			panelScannear.setLayout(flowLayout1);
			panelScannear.add(getBtnScan(), null);
			panelScannear.add(getBtnLimpar(), null);
		}
		return panelScannear;
	}

	/**
	 * This method initializes btnScan
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnScan() {
		if (btnScan == null) {
			btnScan = new JButton();
			btnScan.setText("Scan");
			btnScan.setIcon(new ImageIcon(getClass().getResource(
					caminhoImagens + "HFSScanner-Scanner.png")));
			btnScan.setEnabled(true);
			btnScan.setPreferredSize(new Dimension(100, 26));
			btnScan.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnScanClick();
				}
			});
		}
		return btnScan;
	}

	/**
	 * This method initializes btnLimpar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLimpar() {
		if (btnLimpar == null) {
			btnLimpar = new JButton();
			btnLimpar.setName("btnLimpar");
			btnLimpar.setPreferredSize(new Dimension(100, 26));
			btnLimpar.setIcon(new ImageIcon(getClass().getResource(
					caminhoImagens + "HFSScanner-Limpar.png")));
			btnLimpar.setEnabled(false);
			btnLimpar.setText("Limpar");
			btnLimpar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnLimparClick();
				}
			});
		}
		return btnLimpar;
	}

	public static void enviarDadosViaURL(URL host, String caminho,
			byte[] conteudo) throws ScannerException {
		try {
			String hostname = host.toString();
			String url = hostname + caminho;
			URL cl = new URL(url);
			URLConnection conn = cl.openConnection();
			conn.setDoOutput(true);
			// conn.setUseCaches(false);
			// conn.connect();

			OutputStreamWriter saida = new OutputStreamWriter(
					conn.getOutputStream());
			char[] dados = new char[conteudo.length];
			for (int i = 0; i < conteudo.length; i++) {
				dados[i] = (char) conteudo[i];
			}
			saida.write(dados, 0, dados.length);
			saida.flush();
			saida.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			// String decodedString;
			// while ((decodedString = in.readLine()) != null) {
			// System.out.println(decodedString);
			// }
			in.close();

		} catch (MalformedURLException e) {
			throw new ScannerException(log, e.getMessage());
		} catch (IOException e) {
			throw new ScannerException(log, e.getMessage());
		}
	}

	public void scannearImagem() throws ScannerException {
		ScannerUtil.getInstancia().registrar();

		if (ScannerUtil.getInstancia().abrirScannerPadrao()) {
			if (ScannerUtil.getInstancia().obterImagemParaClipboard()) {
				imagem = RotinasTransferencia.getInstancia()
						.getImagemClipboard();
				if (imagem != null) {
					simagem = new ScannerImagem(imagem);
					getJScrollPane().add(simagem);

					imgLargura = imagem.getWidth(null);
					imgAltura = imagem.getHeight(null);
					setTextoStatus(imgLargura, imgLargura);
					habilitarBotoes(true);

					byte[] dados = RotinasImagem.getInstancia().getBytes(
							imagem, RotinasImagem.FormatoImagem.JPEG);

					byte[] conteudo = Base64.encodeBase64(dados);

					enviarDadosViaURL(this.getCodeBase(), "scanner", conteudo);
				} else {
					throw new ScannerException(log,
							"A imagem não foi gerada corretamente.");
				}
			}

			ScannerUtil.getInstancia().fecharScanner();
		} else {
			ScannerUtil.getInstancia().fecharScanner();
			throw new ScannerException(log, "O Scanner não está disponível.");
		}
	}

	private void setTextoStatus(int largura, int altura) {
		labStatus.setText("Largura: " + largura + " x Altura: " + altura);
	}

	private void redimImagem(int largura, int altura) {
		imgLargura = largura;
		imgAltura = altura;
		Image novaImagem = RotinasImagem.getInstancia().redimensionar(imagem,
				imgLargura, imgAltura);

		getJScrollPane().removeAll();
		getJScrollPane().add(new ScannerImagem(novaImagem));
		setTextoStatus(imgLargura, imgLargura);
	}

	private void btnResetarClick() {
		redimImagem(imagem.getWidth(null), imagem.getHeight(null));
	}

	private void btnAdaptarClick() {
		imgLargura = getJScrollPane().getWidth() - 10;
		imgAltura = getJScrollPane().getHeight() - 10;
		redimImagem(imgLargura, imgAltura);
	}

	private void btnAumentarZoomClick() {
		if (imgLargura < MAX_ZOOM && imgAltura < MAX_ZOOM) {
			imgLargura += ZOOM;
			imgAltura += ZOOM;
			redimImagem(imgLargura, imgAltura);
		}
	}

	private void btnDiminuirZoomClick() {
		if (imgLargura > 0 && imgAltura > 0) {
			imgLargura -= ZOOM;
			imgAltura -= ZOOM;
			redimImagem(imgLargura, imgAltura);
		}
	}

	private void btnScanClick() {
		try {
			scannearImagem();
		} catch (ScannerException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "HFSScanner",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void btnLimparClick() {
		getJScrollPane().removeAll();
		getJScrollPane().repaint();
		habilitarBotoes(false);
		setTextoStatus(0, 0);
	}

	private void habilitarBotoes(boolean habilitar) {
		getBtnResetar().setEnabled(habilitar);
		getBtnAdaptar().setEnabled(habilitar);
		getBtnAumentarZoom().setEnabled(habilitar);
		getBtnDiminuirZoom().setEnabled(habilitar);
		getBtnScan().setEnabled(!habilitar);
		getBtnLimpar().setEnabled(habilitar);
	}

}
