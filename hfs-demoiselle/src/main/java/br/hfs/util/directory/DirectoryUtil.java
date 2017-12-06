package br.hfs.util.directory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class DirectoryUtil extends DirectoryWalker<File> implements
		Serializable {
	private static final long serialVersionUID = 1L;

	private volatile boolean cancelled = false;

	public DirectoryUtil() {
		super();
	}
	
	public DirectoryUtil(FileFilter filter) {
		super(filter, -1);
	}

	public DirectoryUtil(FileFilter filter, int depthLimit) {
		super(filter, depthLimit);
	}

	public static FileFilter filtrarPorExtensao(String extensao) {
		IOFileFilter fooDirFilter = FileFilterUtils.and(
				FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);

		IOFileFilter fooFileFilter = FileFilterUtils.and(
				FileFilterUtils.fileFileFilter(),
				FileFilterUtils.suffixFileFilter(extensao));

		FileFilter fooFilter = FileFilterUtils.or(
				fooDirFilter, fooFileFilter);
		return fooFilter;
	}
	
	public List<File> getArquivos(File diretorio) throws IOException {
		List<File> listaArquivos = new ArrayList<File>();
		walk(diretorio, listaArquivos);
		return listaArquivos;
	}

	protected void handleFile(File file, int depth, Collection<File> results)
			throws IOException {
		results.add(file);

		/*
		 * if (file.getCanonicalPath().endsWith(".docx")) { if (depth > 3) {
		 * System.out.println("depth is " + depth + ", so " +
		 * file.getCanonicalPath() + " will be skipped"); } else {
		 * System.out.println("depth is " + depth + ", so " +
		 * file.getCanonicalPath() + " will be added"); results.add(file); } }
		 * else { System.out.println("file doesn't end in .txt, so " +
		 * file.getCanonicalPath() + " will be skipped"); }
		 */
	}

	protected boolean handleDirectory(File directory, int depth,
			Collection<File> results) {
		return true;
	}

	public void cancel() {
		cancelled = true;
	}

	protected boolean handleIsCancelled(File file, int depth,
			Collection<File> results) {
		return cancelled;
	}

	protected void handleCancelled(File startDirectory,
			Collection<File> results, CancelException cancel) {
		// implement processing required when a cancellation occurs
	}
}
