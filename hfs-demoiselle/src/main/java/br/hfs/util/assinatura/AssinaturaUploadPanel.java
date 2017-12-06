package br.hfs.util.assinatura;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.hfs.util.pdf.PdfException;
import br.hfs.util.pdf.PdfExemplo;
import br.hfs.util.pdf.PdfUtil;

public class AssinaturaUploadPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	//private static final Logger logger = Logger
		//	.getLogger(AssinaturaUploadPanel.class.getName());

	private JButton btnAssinar;
	private JCheckBox chkSalvarAssinados;
	
	private AssinaturaServletUtil asUtil;
	private PdfUtil pdfUtil;
	
	private String urlBase;
	private File[] arquivosSelecionados;
	private List<byte[]> docsLista;	
	private List<byte[]> assinadosLista;
	
	public AssinaturaUploadPanel(String urlBase){
		super(new BorderLayout());
		
		this.urlBase = urlBase;
		
		asUtil = new AssinaturaServletUtil();
		pdfUtil = new PdfUtil();
		docsLista = new ArrayList<byte[]>();
		assinadosLista = new ArrayList<byte[]>();
		
		initComponents();
	}
	
	private void initComponents() {
		btnAssinar = new JButton();

		btnAssinar.setText("Assinar Arquivos");
		btnAssinar.setEnabled(true);
		btnAssinar.setFocusable(true);
		btnAssinar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAssinar.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAssinar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAssinarActionPerformed(evt);
			}
		});
		
		chkSalvarAssinados = new JCheckBox(
				"Deseja salvar os arquivos assinados?", true);
        
		add(chkSalvarAssinados, BorderLayout.CENTER);
		add(btnAssinar, BorderLayout.SOUTH);
	}

	private void btnAssinarActionPerformed(ActionEvent evt) {
		boolean bErro = false;
		docsLista.clear();
		assinadosLista.clear();
		
		btnAdicionarArquivo();
		btnAssinar();

		try {
			String caminho, nome = "";
			File arqAssinado;
			for (int i = 0; i < arquivosSelecionados.length; i++) {
				File arq = arquivosSelecionados[i];
				nome = arq.getName().replaceAll(".pdf","_assinado.pdf");
				asUtil.enviarArquivoToServlet(urlBase, assinadosLista.get(i), nome);
			}
			
			if (chkSalvarAssinados.isSelected()){
				for (int i = 0; i < arquivosSelecionados.length; i++) {
					File arq = arquivosSelecionados[i];
	
					caminho = arq.getPath().replaceAll(".pdf","_assinado.pdf");
					arqAssinado = new File(caminho);		
					
					FileUtils.writeByteArrayToFile(arqAssinado, assinadosLista.get(i));									
				}
			}
			
		} catch (AssinaturaDigitalException e) {
			bErro = true;
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"ERRO", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			bErro = true;
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"ERRO", JOptionPane.ERROR_MESSAGE);
		}
		
		docsLista.clear();
		assinadosLista.clear();
		
		if (!bErro) {
			JOptionPane.showMessageDialog(null, "Arquivos Assinados com Sucesso!", 
				"INFO", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void btnAdicionarArquivo() {		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			arquivosSelecionados = fileChooser.getSelectedFiles();
			adicionarArquivos(arquivosSelecionados);
		}			
	}

	private void adicionarArquivos(File[] files) {
		for (File selectedFile : files) {
			try {
				byte[] dataToSign = FileUtils.readFileToByteArray(selectedFile);
				docsLista.add(dataToSign);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), 
						"ERRO", JOptionPane.ERROR_MESSAGE);
			} 
		}
	}
	
	private void btnAssinar() {
		int res = JOptionPane.showConfirmDialog(this, "Deseja realmente assinar?", "Confirmar", JOptionPane.YES_NO_OPTION);
		
		if (res == JOptionPane.YES_OPTION){
			/*
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));				
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedDir = fileChooser.getSelectedFile();
				assinarArquivos(selectedDir);
			}			
			*/
			assinarArquivos();
			
			//docsLista.clear();
			//btnAssinar.setEnabled(false);			
		}
		
	}

	private void assinarArquivos() {
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
		String[] texto = {
	            "Documento digitalmente assinado em "+ sm.format(new Date()) +", nos termos da Lei 11.419m de 19-12-2006.",
	            "Identificador: 10000007890 http://www.trt1.jus.br/web/guest/conferencia-assinatura-eletronica" };
				
		try {
			for (int i = 0; i < docsLista.size(); i++) {
				//String item = docsLista.get(i);
				
				/*
				URL url = new URL(urlBase + "/temp/" + item);
				arq += "/" + item; 
				FileUtils.copyURLToFile(url, new File(arq));
				*/
				/*
				File arqAssinado = new File(selectedDir + "/" + item + ".p7s");
				byte[] dataToSign = docsLista.get(i);
				byte[] encodedPKCS7 = adUtil.assinarPKCS7_Token(dataToSign);

				if (!arqAssinado.exists()) {
					FileUtils.writeByteArrayToFile(arqAssinado, encodedPKCS7);
				}
				*/
								
				byte[] itemArquivo = docsLista.get(i);				
				byte[] arqSaida = pdfUtil.textoVerticalPDF(itemArquivo, texto);
				byte[] arquivoImagem = IOUtils.toByteArray(PdfExemplo.class.getResourceAsStream("imagemAssinatura.jpg"));
				arqSaida = pdfUtil.addImagemPDF(arqSaida, arquivoImagem, 20, 25);
				//File arqAssinado = new File("C:/Demoiselle/workspace/hfs-demoiselle/assinatura/saida"+i+".pdf");
				byte[] pdfAssinado = pdfUtil.criarAssinaturaVisivelViaToken(new ByteArrayInputStream(arqSaida), null,
						null);
				//FileUtils.writeByteArrayToFile(arqAssinado, pdfAssinado);
				assinadosLista.add(pdfAssinado);
			}
			
		} catch (PdfException e) {
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

}
