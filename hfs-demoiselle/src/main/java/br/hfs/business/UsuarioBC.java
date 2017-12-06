package br.hfs.business;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import br.gov.frameworkdemoiselle.certificate.criptography.DigestAlgorithmEnum;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.hfs.domain.AnexoUsuario;
import br.hfs.domain.Perfil;
import br.hfs.domain.Usuario;
import br.hfs.exception.UsuarioException;
import br.hfs.persistence.UsuarioDAO;
import br.hfs.util.security.SegurancaUtil;
import br.hfs.util.xml.UsuarioXML;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private PerfilBC perfilBC;
	
	@Inject
	private AnexoUsuarioBC anexoUsuarioBC;	
	
	@Inject
	private SegurancaUtil segurancaUtil;

	@Transactional
	public void load() {
		if (findAll().isEmpty()) {
			List<Perfil> perfis = new ArrayList<Perfil>();
			perfis.add(perfilBC.findAll().get(0));

			Usuario usu = new Usuario("henrique.souza", segurancaUtil.HashSimplesHex(
					"abcd1234".getBytes(), DigestAlgorithmEnum.SHA_1),
					"Henrique Figueiredo de Souza",
					"henrique.souza@trt1.jus.br", "02685748474", perfis,
					Long.valueOf(1), new Date(), null, null);
			
			String caminho = "C:/Demoiselle/workspace/hfs-demoiselle/src/main/webapp/resources/images/";
			
			try {
				byte[] foto = FileUtils.readFileToByteArray(new File(caminho + "logo.png"));
				usu.setFoto(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			usu = insert(usu);
			
			
			String[] imagens = {"csv.png", "editar.png",
					"excel.png",
					"locale_en.png",
					"locale_pt.png",
					"logo.png",
					"pdf.png",
					"remover.png",
					"visualizar.png",
					"xml.png"};
			try {	
				byte[] anexo;
				for (String item: imagens) {
					anexo = FileUtils.readFileToByteArray(new File(caminho + item));
					AnexoUsuario au = new AnexoUsuario(item, usu.getId(), anexo);
					anexoUsuarioBC.insert(au);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			

			for (Usuario u : UsuarioXML.carregar("usuario")) {
				insert(new Usuario(u.getLogin(), u.getSenha(), u.getNome(),
						u.getEmail(), null, null, u.getIdUsuarioInclusao(),
						u.getDataInclusao(), null, null));
			}

		}
	}

	public boolean logar(String login, String senha) {
		return getDelegate().logar(login, senha);
	}

	public Usuario autenticado(String login, String senha) {
		return getDelegate().autenticado(login, senha);
	}

	@ExceptionHandler
	public void tratar(UsuarioException e) {
		logger.warning(bundle.getString("usuario.erro"));
		throw e;
	}
}
