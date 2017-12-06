package br.hfs.util.docx;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class DirectoryWalkerExemplo {

	public static void main(String[] args) throws IOException {

		File dir = new File("C:\\Demoiselle\\workspace\\hfs-demoiselle\\docx");

		
		FileFilter fooFilter = filtrarPorExtensao();

		MyDirectoryWalker walker = new MyDirectoryWalker(fooFilter);
		List<File> txtFiles = walker.getTxtFiles(dir);

		for (File txtFile : txtFiles) {
			System.out.println("file:" + txtFile.getCanonicalPath());
		}

	}

	private static FileFilter filtrarPorExtensao() {
		IOFileFilter fooDirFilter = FileFilterUtils.and(
				FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);

		IOFileFilter fooFileFilter = FileFilterUtils.and(
				FileFilterUtils.fileFileFilter(),
				FileFilterUtils.suffixFileFilter(".docx"));

		FileFilter fooFilter = FileFilterUtils.or(
				fooDirFilter, fooFileFilter);
		return fooFilter;
	}

	public static class MyDirectoryWalker extends DirectoryWalker<File> {
		public MyDirectoryWalker() {
			super();
		}
		
	    public MyDirectoryWalker(FileFilter filter) {
	        super(filter, -1);
	     }

		public List<File> getTxtFiles(File dir) throws IOException {
			List<File> results = new ArrayList<File>();
			walk(dir, results);
			return results;
		}

		protected void handleFile(File file, int depth, Collection<File> results)
				throws IOException {
			results.add(file);
			
			/*
			if (file.getCanonicalPath().endsWith(".docx")) {
				if (depth > 3) {
					System.out.println("depth is " + depth + ", so "
							+ file.getCanonicalPath() + " will be skipped");
				} else {
					System.out.println("depth is " + depth + ", so "
							+ file.getCanonicalPath() + " will be added");
					results.add(file);
				}
			} else {
				System.out.println("file doesn't end in .txt, so "
						+ file.getCanonicalPath() + " will be skipped");
			}
			*/
		}

		protected boolean handleDirectory(File directory, int depth,
				Collection<File> results) {
			return true;

		}
	}
}
