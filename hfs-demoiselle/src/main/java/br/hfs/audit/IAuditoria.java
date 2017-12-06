package br.hfs.audit;

import java.util.Date;

public interface IAuditoria {

	public Long getIdUsuarioInclusao();

	public void setIdUsuarioInclusao(Long idUsuarioInclusao);

	public Date getDataInclusao();

	public void setDataInclusao(Date dataInclusao);

	public Long getIdUsuarioAlteracao();

	public void setIdUsuarioAlteracao(Long idUsuarioAlteracao);

	public Date getDataAlteracao();

	public void setDataAlteracao(Date dataAlteracao);
}
