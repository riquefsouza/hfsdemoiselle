package br.hfs.util.barcode;

import org.apache.commons.lang3.StringUtils;

public final class BarCodeUtil {

	public String getBarCodeHTML(BarCodeParams params) {
		BarcodeRequestBean bcrequest = new BarcodeRequestBean();
		bcrequest.setFormat(this.getFormatoImagem(params.getFormatoImagem())); // obrigatorio
		bcrequest.setType(this.getTipoBarCode(params.getTipoBarCode())); // obrigatorio
		bcrequest.setMsg(StringUtils.trimToEmpty(params.getMensagem())); // obrigatorio
		if (params.getAltura() > 0)
			bcrequest.setHeight(Double.toString(params.getAltura()) + "cm");
		if (params.getLarguraModulo() > 0)
			bcrequest.setModuleWidth(Double.toString(params.getLarguraModulo())
					+ "mm");
		if (params.getFatorGrandeza() > 0)
			bcrequest
					.setWideFactor(Integer.toString(params.getFatorGrandeza()));
		if (params.getZonaCalma().trim().length() > 0)
			bcrequest.setQuietZone(StringUtils.trimToEmpty(params
					.getZonaCalma()));
		if (bcrequest.getHumanReadable() != null) {
			if (params.getTextoBarCode().getPosicao() != BarCodeTexto.Posicao.PADRAO)
				bcrequest.setHumanReadable(this.getTextoPosicao(params
						.getTextoBarCode().getPosicao()));
			if (params.getTextoBarCode().getTamanho() > 0)
				bcrequest.setHumanReadableSize(params.getTextoBarCode()
						.getTamanho() + "pt");
			if (params.getTextoBarCode().getFonte() != BarCodeTexto.Fonte.NENHUMA)
				bcrequest.setHumanReadableFont(this.getTextoFonte(params
						.getTextoBarCode().getFonte()));
			if (params.getTextoBarCode().getPadrao().trim().length() > 0)
				bcrequest.sethumanReadablePattern(StringUtils
						.trimToEmpty(params.getTextoBarCode().getPadrao()));
		}
		bcrequest.setSvgEmbed(params.isSVGEmbutido());
		if (params.getResolucao() > 0)
			bcrequest.setResolution(Integer.toString(params.getResolucao()));
		bcrequest.setGray(params.isEscalaCinza());

		return getBarCodeHTML(bcrequest);
	}

	private String getBarCodeHTML(BarcodeRequestBean bcrequest) {
		String html = "";

		final String genbc = bcrequest.toURL();
		if (bcrequest.isSVG()) {
			// "O código de barras gerado no formato SVG (somente é exibido se o formanto SVG é suportado no seu navegador)"
			if (bcrequest.isSvgEmbed()) {
				html = "<embed src='"
						+ genbc
						+ "&ext=.svg' pluginspage='http://www.adobe.com/svg/viewer/install/' width='100%' height='100'/>";
			} else {
				html = "<object type='image/svg+xml' data='"
						+ genbc
						+ "&ext=.svg' name='DynamicBarcode' width='100%' height='100'/>";
			}
		} else if (bcrequest.isBitmap()) {
			// "O código de barras gerado no formato "+bcrequest.getFormat()+
			// " (somente é exibido se "+bcrequest.getFormat()+" é suportado no servidor e no seu navegador)";
			html = "<img src='" + genbc + "'/>";
		} else {
			html = "<p><i>O código de barras gerado não pode ser visualizado. O formato é "
					+ bcrequest.getFormat() + ".</i></p>";
		}
		return html;
	}

	private String getFormatoImagem(BarCodeParams.FormatoImagem formatoImagem) {
		String fmt = "";

		switch (formatoImagem) {
		case SVG:
			fmt = "svg";
			break;
		case EPS:
			fmt = "eps";
			break;
		case JPEG:
			fmt = "jpeg";
			break;
		case TIFF:
			fmt = "tiff";
			break;
		case PNG:
			fmt = "png";
			break;
		case GIF:
			fmt = "gif";
			break;
		}
		return fmt;
	}

	private String getTextoPosicao(BarCodeTexto.Posicao posicao) {
		String fmt = "";

		switch (posicao) {
		// PADRAO, // é sem o parametro
		case TOP:
			fmt = "top";
			break;
		case BOTTOM:
			fmt = "bottom";
			break;
		case NENHUMA:
			fmt = "none";
			break;
		}
		return fmt;
	}

	private String getTextoFonte(BarCodeTexto.Fonte fonte) {
		String fmt = "";

		switch (fonte) {
		case HELVETICA:
			fmt = "Helvetica";
			break;
		case TIMES_NEW_ROMAN:
			fmt = "Times New Roman";
			break;
		case ARIAL:
			fmt = "Arial";
			break;
		case COURIER_NEW:
			fmt = "Courier New";
			break;
		case VERDANA:
			fmt = "Verdana";
			break;
		}

		return fmt;
	}

	private String getTipoBarCode(BarCodeParams.TipoBarCode tipoBarCode) {
		String tipo = "";

		switch (tipoBarCode) {
		case CODABAR:
			tipo = "codabar";
			break;
		case CODE39:
			tipo = "code39";
			break;
		case POSTNET:
			tipo = "postnet";
			break;
		case INTL2OF5:
			tipo = "intl2of5";
			break;
		case EAN_128:
			tipo = "ean-128";
			break;
		case ROYAL_MAIL_CBC:
			tipo = "royal-mail-cbc";
			break;
		case EAN_13:
			tipo = "ean-13";
			break;
		case DATAMATRIX:
			tipo = "datamatrix";
			break;
		case CODE128:
			tipo = "code128";
			break;
		case EAN128:
			tipo = "ean128";
			break;
		case PDF417:
			tipo = "pdf417";
			break;
		case UPC_A:
			tipo = "upc-a";
			break;
		case UPC_E:
			tipo = "upc-e";
			break;
		case USPS4CB:
			tipo = "usps4cb";
			break;
		case EAN_8:
			tipo = "ean-8";
			break;
		}
		return tipo;
	}

}
