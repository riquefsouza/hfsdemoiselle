package br.hfs.util.captcha;

import java.io.Serializable;
import java.util.logging.Logger;

import com.octo.captcha.service.CaptchaServiceException;

public final class CaptchaUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("CaptchaUtil");

	public boolean validar(String captchaId, String resposta)
			throws CaptchaException {
		Boolean isResponseCorrect = Boolean.FALSE;
		// String captchaId = request.getSession().getId();
		// String resposta = request.getParameter("texto");
		try {
			isResponseCorrect = CaptchaServiceSingleton.getInstance()
					.validateResponseForID(captchaId, resposta);

			/*
			 * if (isResponseCorrect){ resposta =
			 * "A resposta ["+resposta+"] está correta!"; } else resposta =
			 * "A resposta ["+resposta+"] NÃO está correta!";
			 */
			return isResponseCorrect;
		} catch (CaptchaServiceException e) {
			throw new CaptchaException(log,
					"Erro ao realizar validação Captcha, " + e.getMessage());
		}
	}

}
