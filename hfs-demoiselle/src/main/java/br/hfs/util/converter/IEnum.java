package br.hfs.util.converter;

import java.io.Serializable;

public abstract interface IEnum<I extends Serializable> extends Serializable {
	
	public abstract String getDescricao();

	public abstract I getId();
}
