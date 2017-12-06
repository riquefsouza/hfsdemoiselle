package br.hfs.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.certificate.criptography.DigestAlgorithmEnum;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.hfs.audit.Auditoria;
import br.hfs.business.AnexoUsuarioBC;
import br.hfs.business.PerfilBC;
import br.hfs.business.UsuarioBC;
import br.hfs.domain.AnexoUsuario;
import br.hfs.domain.Perfil;
import br.hfs.domain.Usuario;
import br.hfs.util.StringUtil;
import br.hfs.util.security.SegurancaUtil;

@ViewController
@PreviousView("/private/usuario_list.xhtml")
public class UsuarioEditMB extends AbstractEditPageBean<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	private UploadedFile arquivo;

	// private StreamedContent conteudo;
	private String conteudo;
	private File fotoArquivo;

	private boolean habilitarUpload;
	private boolean desabilitaAnexo;
	private String nomeArquivo;
	private Long selectedItem;
	private String codigoAnexo;
	private boolean mostrarAnexos;
	private StreamedContent anexoConteudo;

	@Inject
	private UsuarioBC usuarioBC;

	@Inject
	private AnexoUsuario anexoUsuario;

	@Inject
	private AnexoUsuarioBC anexoUsuarioBC;

	private DualListModel<Perfil> perfilList;

	@Inject
	private PerfilBC perfilBC;

	@Inject
	private Auditoria auditoria;

	@Inject
	private SegurancaUtil segurancaUtil;

	@Inject
	private MessageContext messageContext;

	@PostConstruct
	public void init() {
		Usuario bean = this.getBean();
		
		if (bean.getId() == null){
			desabilitaAnexo = true;
		} else {
			desabilitaAnexo = false;
			salvarFotoImagem();
		}
	}

	private void salvarFotoImagem() {
		Usuario bean = this.getBean();
		if (bean.getFoto() != null && bean.getFoto().length > 0) {
			String caminho = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("/temp");
			String fotoImagem = StringUtil.getArquivoRandom("png");
			try {
				fotoArquivo = new File(caminho + File.separatorChar
						+ fotoImagem);
				FileUtils.writeByteArrayToFile(fotoArquivo, bean.getFoto());
			} catch (IOException e) {
				messageContext.add(e.getMessage());
			} finally {
				conteudo = fotoImagem;
			}
		}
	}

	@PreDestroy
	public void destroy() {
		if (fotoArquivo.exists()) {
			fotoArquivo.delete();
		}
	}

	@Override
	@Transactional
	public String delete() {
		this.usuarioBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		Usuario bean = this.getBean();
		if (bean.getSenha() != null) {
			bean.setSenha(segurancaUtil.HashSimplesHex(bean.getSenha()
					.getBytes(), DigestAlgorithmEnum.SHA_1));
		}
		setFoto(bean);
		bean = (Usuario) auditoria.setAuditoria(bean, true);

		this.usuarioBC.insert(bean);
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		Usuario bean = this.getBean();
		if (bean.getSenha() != null) {
			bean.setSenha(segurancaUtil.HashSimplesHex(bean.getSenha()
					.getBytes(), DigestAlgorithmEnum.SHA_1));
		}
		setFoto(bean);
		bean = (Usuario) auditoria.setAuditoria(bean, false);

		this.usuarioBC.update(bean);
		habilitarUpload=true;
		return getPreviousView();
	}

	public void setFoto(Usuario bean) {
		if (arquivo != null) {
			bean.setArquivoFoto(arquivo.getFileName());
			bean.setFoto(arquivo.getContents());
		}
	}

	@Override
	protected Usuario handleLoad(Long id) {
		habilitarUpload=true;
		
		Usuario bean = this.usuarioBC.load(id);
		return bean;
	}

	@Transactional
	public List<AnexoUsuario> getListaAnexo() {
		List<AnexoUsuario> lista = anexoUsuarioBC.listarAnexos(getBean()
				.getId());

		if (lista.size() > 0) {
			mostrarAnexos = true;
		} else {
			mostrarAnexos = false;
		}
		return lista;
	}

	@Transactional
	public void handleFileUpload(FileUploadEvent event) throws Exception {
		String nomeArquivo = StringUtil
				.removeDir(event.getFile().getFileName());
		byte[] conteudo = event.getFile().getContents();

		anexoUsuario = new AnexoUsuario();

		anexoUsuario.setIdUsuario(getBean().getId());
		anexoUsuario.setNomeArquivo(nomeArquivo);
		anexoUsuario.setAnexo(conteudo);
		this.anexoUsuarioBC.insert(anexoUsuario);
		RequestContext.getCurrentInstance().execute("PF('mensagemSucesso').show();");
	}

	public void metodo(String fileName, String idAnexo) {
		nomeArquivo = fileName;
		codigoAnexo = idAnexo;
	}

	public void removerAnexo(ActionEvent event) throws Exception {
		try {
			anexoUsuarioBC.delete(selectedItem);
			RequestContext.getCurrentInstance().execute(
					"PF('mensagemSucesso').show();");
		} catch (Exception e) {
			RequestContext.getCurrentInstance()
					.execute("PF('mensagemFalha').show();");
		}
	}

	public void setPerfilList(DualListModel<Perfil> perfilList) {
		this.perfilList = perfilList;
	}

	public void addPerfilList(List<Perfil> perfilList) {
		this.getBean().getPerfis().addAll(perfilList);
	}

	public void deletePerfilList(List<Perfil> perfilList) {
		this.getBean().getPerfis().removeAll(perfilList);
	}

	public DualListModel<Perfil> getPerfilList() {
		if (this.perfilList == null) {
			List<Perfil> source = perfilBC.findAll();
			List<Perfil> target = this.getBean().getPerfis();

			if (source == null) {
				source = new ArrayList<Perfil>();
			}
			if (target == null) {
				target = new ArrayList<Perfil>();
			} else {
				source.removeAll(target);
			}
			this.perfilList = new DualListModel<Perfil>(source, target);

		}
		return this.perfilList;
	}

	@SuppressWarnings("unchecked")
	public void onTransfer(TransferEvent event) {
		if (event.isAdd()) {
			this.addPerfilList((List<Perfil>) event.getItems());
		}
		if (event.isRemove()) {
			this.deletePerfilList((List<Perfil>) event.getItems());
		}
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public boolean isHabilitarUpload() {
		return habilitarUpload;
	}

	public void setHabilitarUpload(boolean habilitarUpload) {
		this.habilitarUpload = habilitarUpload;
	}

	public boolean isDesabilitaAnexo() {
		return desabilitaAnexo;
	}

	public void setDesabilitaAnexo(boolean desabilitaAnexo) {
		this.desabilitaAnexo = desabilitaAnexo;
	}

	public Long getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Long selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getCodigoAnexo() {
		return codigoAnexo;
	}

	public void setCodigoAnexo(String codigoAnexo) {
		this.codigoAnexo = codigoAnexo;
	}

	public boolean isMostrarAnexos() {
		return mostrarAnexos;
	}

	public void setMostrarAnexos(boolean mostrarAnexos) {
		this.mostrarAnexos = mostrarAnexos;
	}

	public StreamedContent getAnexoConteudo() {
		anexoUsuario = this.anexoUsuarioBC.load(Long.valueOf(codigoAnexo));

		String caminho = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/anexos/" + nomeArquivo);

		byte[] bFile = anexoUsuario.getAnexo();
		ByteArrayInputStream stream = new ByteArrayInputStream(bFile);
		anexoConteudo = new DefaultStreamedContent(stream, caminho, nomeArquivo);
		return anexoConteudo;
	}

	public void setAnexoConteudo(StreamedContent anexoConteudo) {
		this.anexoConteudo = anexoConteudo;
	}

}