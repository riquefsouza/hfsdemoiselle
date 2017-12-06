package br.hfs.util.scanner;

import java.util.logging.Logger;

import com.sun.jna.Native;

public final class ScannerUtil {
	private static Logger log = Logger.getLogger("RotinasScanner");
	private static ScannerUtil instancia;
	private EZTwainLibrary biblioteca;

	private ScannerUtil() {
		super();
	}

	public static ScannerUtil getInstancia() {
		if (instancia == null) {
			instancia = new ScannerUtil();
		}
		return instancia;
	}

	public void lerBiblioteca(String caminho) {
		biblioteca = (EZTwainLibrary) Native.loadLibrary(
				caminho + "EZTW32.dll", EZTwainLibrary.class);
	}

	public void registrar() {
		String versao = "1.00";
		String autor = "by HFS";
		String familia = "HFS";
		String produto = "HFS GWT Componentes";

		biblioteca.TWAIN_RegisterApp(1, 0, EZTwainLibrary.TWLG_POR,
				EZTwainLibrary.TWCY_BRAZIL, versao.toCharArray(), autor
						.toCharArray(), familia.toCharArray(), produto
						.toCharArray());
	}

	public boolean abrirScannerPadrao() {
		return (biblioteca.TWAIN_OpenDefaultSource() == 1);
	}

	public boolean obterNativo() {
		return (biblioteca.TWAIN_AcquireNative(0, 0) == 1);
	}

	public boolean obterImagemParaClipboard() {
		return (biblioteca.TWAIN_AcquireToClipboard(0, 0) == 1);
	}

	// 0 success
	// -1 Acquire failed OR user cancelled File Save dialog
	// -2 file open error (invalid path or name, or access denied)
	// -3 (weird) unable to lock DIB - probably an invalid handle.
	// -4 writing BMP data failed, possibly output device is full
	public boolean obterImagemParaArquivo(String arquivo)
			throws ScannerException {
		int resultado;
		resultado = biblioteca
				.TWAIN_AcquireToFilename(0, arquivo.toCharArray());

		if (resultado == 0)
			return true;
		else if (resultado == -1)
			throw new ScannerException(log,
					"Falha ao obter imagem ou o usuário cancelou o diálogo de salvar arquivo.");
		else if (resultado == -2)
			throw new ScannerException(log,
					"Falha ao abrir arquivo (caminho ou nome inválido, ou acesso negado).");
		else if (resultado == -3)
			throw new ScannerException(
					log,
					"(estranho) não foi possível travar DIB - provavelmente o manuseador é inválido.");
		else if (resultado == -4)
			throw new ScannerException(
					log,
					"Falha ao escreve arquivo bitmap, possivelmente dispositivo de saída está cheio.");
		else
			return false;
	}

	public void fecharScanner() {
		biblioteca.TWAIN_CloseSource();
	}
	
	public String getScannerApplet(String url, int largura, int altura) {
		if (largura < 300) {
			largura = 300;
		}
		if (altura < 200) {
			altura = 200;		
		}
		
		int naltura = (altura * 85) / 100;
		return "<html><body>" +
		"<applet code='com/hfsgwt/server/scanner/ScannerApplet' width='"+largura+"px' height='"+naltura+"px' "+ 
		"archive='scanner/lib/log4j.jar,scanner/lib/platform.jar,scanner/lib/jna.jar,scanner/lib/HFSScanner.jar,scanner/lib/commons-codec-1.4.jar'> "+
		"<param name='caminhodll' value='c:/windows/system32/' /> "+
		"Desculpe, Seu navegador não suporta Java applet! "+
		"</applet>" +
		"<font color='red' size=1><b>SOMENTE FUNCIONA NOS SISTEMAS OPERACIONAIS BASEADOS NO WINDOWS.</b></font><br> "+		
		"<font color='blue' size=1><b>Para executar o scaneamento, descompacte e execute este <a href='"+url+"/scanner.zip'>arquivo</a> e reinicie o navegador.</b></font> "+		
		"</body></html> ";
	}
	
}
