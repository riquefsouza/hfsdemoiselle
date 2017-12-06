package br.hfs.util.assinatura;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.io.FileUtils;

import br.hfs.util.certificado.CertificadoException;
import br.hfs.util.certificado.CertificadoUtil;
import br.hfs.util.certificado.CertificadoX509;
import br.hfs.util.security.CertificadoDigitalVO;

public class AssinaturaPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(AssinaturaPanel.class.getName());

	private JToolBar barraFerra;
	private JButton btnAdicionarArquivo;
	private JButton btnAssinar;
	private JButton btnLimparTudo;
	private JButton btnRemoverArquivo;
	private JButton btnVisualizarCertificado;
	private JSplitPane jSplitPane1;
	private JList<String> listaArquivos;
	private JScrollPane scrollPanelArquivos;
	private JScrollPane scrollPanelTabela;
	private JTable tabelaCertificado;

	private DefaultListModel<String> modeloLista;
	private DefaultTableModel modeloTabela;
	
	private CertificadoUtil cUtil;
	//private AssinaturaServletUtil asUtil;
	private AssinaturaDigitalUtil adUtil;

	private boolean isApplet;
	private String urlBase;
	private List<byte[]> docsLista;	
	
	public AssinaturaPanel(boolean isApplet, String urlBase){
		super(new BorderLayout());
		
		this.isApplet = isApplet; 
		this.urlBase = urlBase;
		
		cUtil = new CertificadoUtil();
		//asUtil = new AssinaturaServletUtil();
		adUtil = new AssinaturaDigitalUtil();
		docsLista = new ArrayList<byte[]>();
		
		modeloLista = new DefaultListModel<String>();
		modeloTabela = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex,
					int columnIndex) {
				return false;
			}
		};
		modeloTabela.addColumn("Propriedade");
		modeloTabela.addColumn("Valor");
		
		initComponents();
	}
	
	private void initComponents() {
		barraFerra = new JToolBar();
		btnVisualizarCertificado = new JButton();
		btnAdicionarArquivo = new JButton();
		btnRemoverArquivo = new JButton();
		btnAssinar = new JButton();
		btnLimparTudo = new JButton();
		jSplitPane1 = new JSplitPane();
		scrollPanelArquivos = new JScrollPane();
		listaArquivos = new JList<String>();
		scrollPanelTabela = new JScrollPane();
		tabelaCertificado = new JTable();

		setPreferredSize(new Dimension(700, 650));

		barraFerra.setFloatable(false);

		btnVisualizarCertificado.setText("Visualizar Certificado");
		btnVisualizarCertificado.setFocusable(false);
		btnVisualizarCertificado
				.setHorizontalTextPosition(SwingConstants.CENTER);
		btnVisualizarCertificado.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnVisualizarCertificado
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnVisualizarCertificadoActionPerformed(evt);
					}
				});
		barraFerra.add(btnVisualizarCertificado);

		btnAdicionarArquivo.setText("Adicionar Arquivo");
		btnAdicionarArquivo.setEnabled(false);
		btnAdicionarArquivo.setFocusable(false);
		btnAdicionarArquivo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAdicionarArquivo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAdicionarArquivo
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnAdicionarArquivoActionPerformed(evt);
					}
				});
		barraFerra.add(btnAdicionarArquivo);

		btnRemoverArquivo.setText("Remover Arquivo");
		btnRemoverArquivo.setEnabled(false);
		btnRemoverArquivo.setFocusable(false);
		btnRemoverArquivo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRemoverArquivo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRemoverArquivo
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						btnRemoverArquivoActionPerformed(evt);
					}
				});
		barraFerra.add(btnRemoverArquivo);

		btnAssinar.setText("Assinar Arquivos");
		btnAssinar.setEnabled(false);
		btnAssinar.setFocusable(false);
		btnAssinar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAssinar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAssinar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAssinarActionPerformed(evt);
			}
		});
		barraFerra.add(btnAssinar);

		btnLimparTudo.setText("Limpar");
		btnLimparTudo.setFocusable(false);
		btnLimparTudo.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLimparTudo.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLimparTudo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnLimparTudoActionPerformed(evt);
			}
		});
		barraFerra.add(btnLimparTudo);

		add(barraFerra, BorderLayout.PAGE_START);

		jSplitPane1.setDividerLocation(100);
		jSplitPane1.setDividerSize(8);
		jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);

		listaArquivos.setModel(modeloLista);
		listaArquivos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				listaArquivosMouseClicked(evt);
			}
		});
		scrollPanelArquivos.setViewportView(listaArquivos);

		jSplitPane1.setLeftComponent(scrollPanelArquivos);

		tabelaCertificado.setModel(modeloTabela);
		tabelaCertificado.setRowSelectionAllowed(true);
		tabelaCertificado.setColumnSelectionAllowed(false);
		scrollPanelTabela.setViewportView(tabelaCertificado);
		tabelaCertificado.getColumnModel().getSelectionModel()
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tabelaCertificado.getColumnModel().getColumn(0).setMaxWidth(150);
		tabelaCertificado.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		//tabelaCertificado.getColumnModel().getColumn(0).setCellRenderer(new MyCellRenderer());
		tabelaCertificado.getColumnModel().getColumn(1).setCellRenderer(new MyCellRenderer());
		
		jSplitPane1.setRightComponent(scrollPanelTabela);

		add(jSplitPane1, BorderLayout.CENTER);
	}

	private void btnVisualizarCertificadoActionPerformed(
			ActionEvent evt) {
		try {
			btnAdicionarArquivo.setEnabled(true);
			btnVisualizarCertificado.setEnabled(false);
			
			CertificadoX509 cx1 = cUtil.getCertificadoTokenWindows();
			List<CertificadoDigitalVO> lista = cx1.toLista(); 
			
			modeloTabela.setNumRows(lista.size());
			for (int i = 0; i < lista.size(); i++) {
				modeloTabela.setValueAt(lista.get(i).getItem(), i, 0);
				modeloTabela.setValueAt(lista.get(i).getDescricao(), i, 1);
			}
			
		} catch (CertificadoException e) {
			btnAdicionarArquivo.setEnabled(false);
			btnVisualizarCertificado.setEnabled(true);
			
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"ERRO", JOptionPane.ERROR_MESSAGE);			
		}
	}

	private void btnAdicionarArquivoActionPerformed(
			ActionEvent evt) {
		
		if (isApplet){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(true);
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File[] files = fileChooser.getSelectedFiles();
				adicionarArquivos(files);
			}			
		} else {
			try {
				FileContents[] fc = abrirArquivosViaJNLP();
				if (fc!=null && fc.length > 0){
					File[] files = new File[fc.length];
					for (int i = 0; i < files.length; i++) {
						files[i] = new File(fc[i].getName());
					}
					adicionarArquivos(files);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), 
						"ERRO", JOptionPane.ERROR_MESSAGE);			
			}	
		}
		
		btnRemoverArquivo.setEnabled((modeloLista.size() > 0));
		btnAssinar.setEnabled((modeloLista.size() > 0));
	}

	private void adicionarArquivos(File[] files) {
		for (File selectedFile : files) {
			try {
				byte[] dataToSign = FileUtils.readFileToByteArray(selectedFile);
				docsLista.add(dataToSign);
				modeloLista.addElement(selectedFile.getName());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), 
						"ERRO", JOptionPane.ERROR_MESSAGE);
			} 
			/*
			try {
				asUtil.enviarArquivoToServlet(urlBase, selectedFile.toString());
				bEnviou = true;
			} catch (AssinaturaDigitalException e) {
				bEnviou = false;
				JOptionPane.showMessageDialog(null, e.getMessage(), 
						"ERRO", JOptionPane.ERROR_MESSAGE);
			}
			*/						
		}
	}

	@SuppressWarnings("unchecked")
	private void listaArquivosMouseClicked(MouseEvent evt) {
		JList<String> lista = (JList<String>) evt.getSource();
		if (evt.getClickCount() == 2) {
			int index = lista.locationToIndex(evt.getPoint());
			if (index >= 0) {
				modeloLista.remove(index);
				docsLista.remove(index);

				btnRemoverArquivo.setEnabled((modeloLista.size() > 0));
				btnAssinar.setEnabled((modeloLista.size() > 0));
			}
		}
	}

	private void btnRemoverArquivoActionPerformed(ActionEvent evt) {
		int[] items = listaArquivos.getSelectedIndices();
		for (int i = items.length - 1; i >= 0; i--) {
			modeloLista.remove(items[i]);
			docsLista.remove(items[i]);
		}
		btnRemoverArquivo.setEnabled((modeloLista.size() > 0));
		btnAssinar.setEnabled((modeloLista.size() > 0));
	}

	private void btnAssinarActionPerformed(ActionEvent evt) {
		int res = JOptionPane.showConfirmDialog(this, "Deseja realmente assinar?", "Confirmar", JOptionPane.YES_NO_OPTION);
		
		if (res == JOptionPane.YES_OPTION){
			
			if (isApplet){
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));				
				int result = fileChooser.showOpenDialog(this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedDir = fileChooser.getSelectedFile();
					assinarArquivos(selectedDir);
				}			
				
			} else {
				try {
					FileContents fc = abrirArquivoViaJNLP();
					if (fc!=null){
						File selectedDir = new File(fc.getName());
						assinarArquivos(selectedDir);
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), 
							"ERRO", JOptionPane.ERROR_MESSAGE);			
				}	
			}
			
			modeloLista.clear();
			docsLista.clear();
			btnAdicionarArquivo.setEnabled(true);
			btnRemoverArquivo.setEnabled(false);
			btnAssinar.setEnabled(false);			
		}
		
		/*
		 * JOptionPane.showMessageDialog(null, "alert", "alert",
		 * JOptionPane.ERROR_MESSAGE);
		 * 
		 * JOptionPane.showConfirmDialog(null, "choose one", "choose one",
		 * JOptionPane.YES_NO_OPTION);
		 * 
		 * Object[] options = { "OK", "CANCEL" };
		 * JOptionPane.showOptionDialog(null, "Click OK to continue", "Warning",
		 * JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
		 * options, options[0]);
		 * 
		 * String inputValue =
		 * JOptionPane.showInputDialog("Please input a value");
		 * 
		 * Object[] possibleValues = { "First", "Second", "Third" }; Object
		 * selectedValue = JOptionPane.showInputDialog(null, "Choose one",
		 * "Input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
		 * possibleValues[0]);
		 */
	}

	private void assinarArquivos(File selectedDir) {
		try {
			//List<String> arquivos = listaArquivos.getSelectedValuesList();
			for (int i = 0; i < modeloLista.size(); i++) {
				String item = modeloLista.get(i);
				
				/*
				URL url = new URL(urlBase + "/temp/" + item);
				arq += "/" + item; 
				FileUtils.copyURLToFile(url, new File(arq));
				*/
				
				File arqAssinado = new File(selectedDir + "/" + item + ".p7s");
				byte[] dataToSign = docsLista.get(i);
				byte[] encodedPKCS7 = adUtil.assinarPKCS7_Token(dataToSign);

				if (!arqAssinado.exists()) {
					FileUtils.writeByteArrayToFile(arqAssinado, encodedPKCS7);
				}

			}
		} catch (AssinaturaDigitalException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"ERRO", JOptionPane.ERROR_MESSAGE);						
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"ERRO", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void btnLimparTudoActionPerformed(ActionEvent evt) {
		modeloLista.clear();
		docsLista.clear();
		modeloTabela.setNumRows(0);
		btnVisualizarCertificado.setEnabled(true);
		btnAdicionarArquivo.setEnabled(false);
		btnRemoverArquivo.setEnabled(false);
		btnAssinar.setEnabled(false);
	}

	private FileContents abrirArquivoViaJNLP() {
		FileOpenService fos = null;
		FileContents fileContents = null;

		try {
			fos = (FileOpenService) ServiceManager
					.lookup("javax.jnlp.FileOpenService");
		} catch (UnavailableServiceException exc) {
			logger.log(Level.SEVERE, "Serviço [javax.jnlp.FileOpenService] não disponível!", exc);
		}

		if (fos != null) {
			try {
				fileContents = fos.openFileDialog(null, null);
			} catch (Exception exc) {
				logger.log(Level.SEVERE, "Abrir o arquivo falhou!", exc);
			}
		}

		return fileContents;
	}

	private FileContents[] abrirArquivosViaJNLP() {
		FileOpenService fos = null;
		FileContents[] fileContents = null;

		try {
			fos = (FileOpenService) ServiceManager
					.lookup("javax.jnlp.FileOpenService");
		} catch (UnavailableServiceException exc) {
			logger.log(Level.SEVERE, "Serviço [javax.jnlp.FileOpenService] não disponível!", exc);
		}

		if (fos != null) {
			try {
				fileContents = fos.openMultiFileDialog(null, null);
			} catch (Exception exc) {
				logger.log(Level.SEVERE, "Abrir o arquivo falhou!", exc);
			}
		}

		return fileContents;
	}
	
/*	
	private void salvarArquivoViaJNLP(byte[] arquivo, String nomeArquivo){
		FileSaveService fss = null;
		FileContents fileContents = null;
		ByteArrayInputStream is = new ByteArrayInputStream(arquivo);
		
		try {
			fss = (FileSaveService) ServiceManager
					.lookup("javax.jnlp.FileSaveService");
		} catch (UnavailableServiceException exc) {
			logger.log(Level.SEVERE, "Serviço [javax.jnlp.FileSaveService] não disponível!", exc);
		}

		if (fss != null) {
			try {
				fileContents = fss.saveFileDialog(null, null, is, nomeArquivo);
			} catch (Exception exc) {
				logger.log(Level.SEVERE, "Falha ao salvar o arquivo!", exc);
			}
		}

		if (fileContents != null) {
			try {
				logger.log(Level.INFO, "Arquivo salvo: " + fileContents.getName());
			} catch (IOException exc) {
				logger.log(Level.SEVERE, "Problema salvando arquivo!", exc);
			}
		} else {
			logger.log(Level.INFO, "Salvamento de arquivo cancelada!");
		}		
	}
	*/
}

class MyCellRenderer extends JTextArea implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public MyCellRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setText(value.toString());
		setSize(table.getColumnModel().getColumn(column).getWidth(),
				getPreferredSize().height);
		if (table.getRowHeight(row) != getPreferredSize().height) {
			table.setRowHeight(row, getPreferredSize().height);
		}
		return this;
	}
}
