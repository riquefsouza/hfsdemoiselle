package br.hfs.view.exemplos;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class EditorView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
