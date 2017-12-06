package br.hfs.view.apiexemplos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.hfs.util.barcode.BarCodeParams;
import br.hfs.util.barcode.BarCodeUtil;
import br.hfs.util.barcode.BarCodeParams.TipoBarCode;

@ManagedBean
public class BarcodeView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tipo;
	private List<SelectItem> listaTipo;
	private String numero;
	private String barcodeHtml;

	@Inject
	private BarCodeUtil barcodeUtil;

	@PostConstruct
	public void init() {
		listaTipo = new ArrayList<SelectItem>();
		for (TipoBarCode item : BarCodeParams.TipoBarCode.values()) {
			listaTipo.add(new SelectItem(item.name(), item.name()));
		}
		numero = "123456";
	}

	public void gerar() {
		BarCodeParams params = new BarCodeParams();
		params.setFormatoImagem(BarCodeParams.FormatoImagem.JPEG);
		params.setTipoBarCode(BarCodeParams.TipoBarCode.valueOf(tipo));
		params.setMensagem(numero);
		barcodeHtml = barcodeUtil.getBarCodeHTML(params);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<SelectItem> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List<SelectItem> listaTipo) {
		this.listaTipo = listaTipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBarcodeHtml() {
		return barcodeHtml;
	}

	public void setBarcodeHtml(String barcodeHtml) {
		this.barcodeHtml = barcodeHtml;
	}

	public BarCodeUtil getBarcodeUtil() {
		return barcodeUtil;
	}

	public void setBarcodeUtil(BarCodeUtil barcodeUtil) {
		this.barcodeUtil = barcodeUtil;
	}

}
