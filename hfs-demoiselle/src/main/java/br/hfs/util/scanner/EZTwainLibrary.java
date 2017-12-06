package br.hfs.util.scanner;

import com.sun.jna.Library;

public interface EZTwainLibrary extends Library {
	static final int TWLG_POR = 10;
	static final int TWCY_BRAZIL = 55;

	void TWAIN_RegisterApp(int nMajorNum, int nMinorNum, int nLanguage,
			int nCountry, char[] lpszVersion, char[] lpszMfg,
			char[] lpszFamily, char[] lpszProduct);

	int TWAIN_OpenDefaultSource();

	long TWAIN_AcquireNative(long hwndApp, long wPixTypes);

	int TWAIN_AcquireToClipboard(long hwndApp, long wPixTypes);

	int TWAIN_AcquireToFilename(long hwndApp, char[] sFile);

	int TWAIN_CloseSource();

}
