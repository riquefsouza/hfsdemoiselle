package br.hfs.util.xml;

import java.util.Arrays;
import java.util.List;

import br.hfs.domain.Usuario;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ROWDATA")
public class Rowdata {	
	
	@XStreamImplicit(itemFieldName="ROW")
	private List<Usuario> row;
	
	public Rowdata(Usuario... row) {
		super();
		this.row = Arrays.asList(row);
	}

	public List<Usuario> getRow() {
		return row;
	}

	public void setRow(List<Usuario> row) {
		this.row = row;
	}
}
