package br.hfs.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public final class VisitarDiretorio {
	private static VisitarDiretorio instancia;

	private ArrayList<String> listaDir;
	private ArrayList<File> listaFile;

	private VisitarDiretorio() {
		listaDir = new ArrayList<String>();
		listaFile = new ArrayList<File>();
	}

	public static VisitarDiretorio getInstancia() {
		if (instancia == null) {
			instancia = new VisitarDiretorio();
		}
		return instancia;
	}

	public ArrayList<String> getListaDir() {
		return listaDir;
	}

	public ArrayList<File> getListaFile() {
		return listaFile;
	}

	public boolean ListarDiretorio(String sCaminho, final String sExtensao) {
		listaDir.clear();
		listaFile.clear();
		FilenameFilter filtro = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(sExtensao);
			}
		};
		visitaTodosDiretoriosArquivos(new File(sCaminho), filtro);
		listaDir.remove(0); // sempre vai remover o primeiro caminho
		listaFile.remove(0);
		return (listaDir.size() > 0);
	}

	public boolean ListarDiretorio(String sCaminho) {
		listaDir.clear();
		listaFile.clear();
		visitaTodosDiretoriosArquivos(new File(sCaminho));
		listaDir.remove(0); // sempre vai remover o primeiro caminho
		listaFile.remove(0);
		return (listaDir.size() > 0);
	}

	private void processaTodosDiretoriosArquivos(File dir) {
		listaFile.add(dir);
		listaDir.add(dir.getAbsolutePath());
	}

	private void visitaTodosDiretoriosArquivos(File dir, FilenameFilter filtro) {
		processaTodosDiretoriosArquivos(dir);

		if (dir.isDirectory()) {
			String[] children = dir.list(filtro);
			for (int i = 0; i < children.length; i++) {
				visitaTodosDiretoriosArquivos(new File(dir, children[i]),
						filtro);
			}
		}
	}

	private void visitaTodosDiretoriosArquivos(File dir) {
		processaTodosDiretoriosArquivos(dir);

		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				visitaTodosDiretoriosArquivos(new File(dir, children[i]));
			}
		}
	}
}