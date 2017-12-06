package br.hfs.domain.lazydatamodel;

import java.lang.reflect.Method;
import java.util.Date;

import br.hfs.domain.Usuario;
import br.hfs.util.StringUtil;

public class UsuarioLazyExemplo {

	public static void main(String[] args) throws Exception {
		Usuario usuario1 = new Usuario(Long.valueOf(1), "henrique.souza", "abcd1234",
				"Henrique Figueiredo de Souza", 
				"henrique.souza@trt1.jus.br", "02685748474", 
				null, Long.valueOf(1), new Date(),
				null, null);
		
		Method metodo = usuario1.getClass().getMethod(StringUtil.cap("login"), new Class[] {});
		Object str1 = metodo.invoke(usuario1, new Object[] {});
		System.out.println("retorno: " + str1);
	}

}
