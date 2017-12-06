package br.hfs.util.json;

import org.primefaces.json.JSONObject;

public interface IJsonUtilListener {
	void carregando(long total, long ordem, JSONObject jsonObject);
}
