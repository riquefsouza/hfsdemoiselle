package br.hfs.util.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import br.gov.frameworkdemoiselle.certificate.extension.DefaultExtension;
import br.gov.frameworkdemoiselle.certificate.extension.DefaultExtensionType;
import br.gov.frameworkdemoiselle.certificate.extension.ICPBrasilExtension;
import br.gov.frameworkdemoiselle.certificate.extension.ICPBrasilExtensionType;

public class CertificadoDigital implements Serializable {
	private static final long serialVersionUID = 1L;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.NOME)
	private String nome;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.CPF)
	private String cpf;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.CNPJ)
	private String cnpj;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.CEI)
	private String cei;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.CEI_PESSOA_FISICA)
	private String ceiPessoaFisica;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.CEI_PESSOA_JURIDICA)
	private String ceiPessoaJuridica;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.DATA_NASCIMENTO)
	private String dataNascimento;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.EMAIL)
	private String email;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.MUNICIPIO_TITULO_ELEITOR)
	private String municipioTituloEleitor;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.NIS)
	private String nis;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.NIVEL_CERTIFICADO)
	private String nivelCertificado;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.NOME_EMPRESARIAL)
	private String nomeEmpresarial;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.NOME_RESPONSAVEL_PESSOA_JURIDICA)
	private String nomeResponsavelPJ;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.NUMERO_IDENTIDADE)
	private String numeroIdentidade;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.NUMERO_TITULO_ELEITOR)
	private String numeroTituloEleitor;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.ORGAO_EXPEDIDOR_IDENTIDADE)
	private String orgaoExpedidorIdentidade;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.PIS_PASEP)
	private String pisPasep;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.SECAO_TITULO_ELEITOR)
	private String secaoTituloEleitor;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.TIPO_CERTIFICADO)
	private String tipoCertificado;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.UF_ORGAO_EXPEDIDOR_IDENTIDADE)
	private String ufOrgaoExpedidorIdentidade;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.UF_TITULO_ELEITOR)
	private String ufTituloEleitor;

	@ICPBrasilExtension(type = ICPBrasilExtensionType.ZONA_TITULO_ELEITOR)
	private String zonaTituloEleitor;

	@DefaultExtension(type = DefaultExtensionType.AFTER_DATE)
	private Date depoisData;

	@DefaultExtension(type = DefaultExtensionType.AUTHORITY_KEY_IDENTIFIER)
	private String identificadorChaveAutoridade;

	@DefaultExtension(type = DefaultExtensionType.BEFORE_DATE)
	private Date antesData;

	@DefaultExtension(type = DefaultExtensionType.CERTIFICATION_AUTHORITY)
	private Boolean autoridadeCertificacao;

	@DefaultExtension(type = DefaultExtensionType.CRL_URL)
	private List<String> listaRevogacaoCertificados;

	@DefaultExtension(type = DefaultExtensionType.ISSUER_DN)
	private String nomeDistintoEmitente;

	@DefaultExtension(type = DefaultExtensionType.KEY_USAGE)
	private String usoChave;

	@DefaultExtension(type = DefaultExtensionType.PATH_LENGTH)
	private Integer tamanhoCaminho;

	@DefaultExtension(type = DefaultExtensionType.SERIAL_NUMBER)
	private String numeroSerial;

	@DefaultExtension(type = DefaultExtensionType.SUBJECT_DN)
	private String nomeDistintoSujeito;

	@DefaultExtension(type = DefaultExtensionType.SUBJECT_KEY_IDENTIFIER)
	private String identificadorChaveSujeito;

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getCei() {
		return cei;
	}

	public String getCeiPessoaFisica() {
		return ceiPessoaFisica;
	}

	public String getCeiPessoaJuridica() {
		return ceiPessoaJuridica;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public String getMunicipioTituloEleitor() {
		return municipioTituloEleitor;
	}

	public String getNis() {
		return nis;
	}

	public String getNivelCertificado() {
		return nivelCertificado;
	}

	public String getNomeEmpresarial() {
		return nomeEmpresarial;
	}

	public String getNomeResponsavelPJ() {
		return nomeResponsavelPJ;
	}

	public String getNumeroIdentidade() {
		return numeroIdentidade;
	}

	public String getNumeroTituloEleitor() {
		return numeroTituloEleitor;
	}

	public String getOrgaoExpedidorIdentidade() {
		return orgaoExpedidorIdentidade;
	}

	public String getPisPasep() {
		return pisPasep;
	}

	public String getSecaoTituloEleitor() {
		return secaoTituloEleitor;
	}

	public String getTipoCertificado() {
		return tipoCertificado;
	}

	public String getUfOrgaoExpedidorIdentidade() {
		return ufOrgaoExpedidorIdentidade;
	}

	public String getUfTituloEleitor() {
		return ufTituloEleitor;
	}

	public String getZonaTituloEleitor() {
		return zonaTituloEleitor;
	}

	public Date getDepoisData() {
		return depoisData;
	}

	public String getIdentificadorChaveAutoridade() {
		return identificadorChaveAutoridade;
	}

	public Date getAntesData() {
		return antesData;
	}

	public Boolean getAutoridadeCertificacao() {
		return autoridadeCertificacao;
	}

	public List<String> getListaRevogacaoCertificados() {
		return listaRevogacaoCertificados;
	}

	public String getNomeDistintoEmitente() {
		return nomeDistintoEmitente;
	}

	public String getUsoChave() {
		return usoChave;
	}

	public Integer getTamanhoCaminho() {
		return tamanhoCaminho;
	}

	public String getNumeroSerial() {
		return numeroSerial;
	}

	public String getNomeDistintoSujeito() {
		return nomeDistintoSujeito;
	}

	public String getIdentificadorChaveSujeito() {
		return identificadorChaveSujeito;
	}

	public List<CertificadoDigitalVO> toLista(){
		List<CertificadoDigitalVO> lista = new ArrayList<CertificadoDigitalVO>();
		lista.add(new CertificadoDigitalVO("Nome", nome));
		lista.add(new CertificadoDigitalVO("CPF", cpf));
		lista.add(new CertificadoDigitalVO("CNPJ", cnpj));
		lista.add(new CertificadoDigitalVO("Cadastro Específico do INSS", cei));
		lista.add(new CertificadoDigitalVO("Cadastro Específico do INSS para Pessoa Física", ceiPessoaFisica));
		lista.add(new CertificadoDigitalVO("Cadastro Específico do INSS para Pessoa Jurídica", ceiPessoaJuridica));
		lista.add(new CertificadoDigitalVO("Data de Nascimento", dataNascimento));
		lista.add(new CertificadoDigitalVO("E-mail", email));
		lista.add(new CertificadoDigitalVO("Município do Título de Eleitor", municipioTituloEleitor));
		lista.add(new CertificadoDigitalVO("NIS", nis));
		lista.add(new CertificadoDigitalVO("Nível do Certificado", nivelCertificado));
		lista.add(new CertificadoDigitalVO("Nome Empresarial", nomeEmpresarial));
		lista.add(new CertificadoDigitalVO("Nome do Responsável da Pessoa Jurídica", nomeResponsavelPJ));
		lista.add(new CertificadoDigitalVO("Número da Identidade", numeroIdentidade));
		lista.add(new CertificadoDigitalVO("Número do Título de Eleitor", numeroTituloEleitor));
		lista.add(new CertificadoDigitalVO("Órgão Expedidor da Identidade", orgaoExpedidorIdentidade));
		lista.add(new CertificadoDigitalVO("PIS / PASEP", pisPasep));
		lista.add(new CertificadoDigitalVO("Seção do Título de Eleitor", secaoTituloEleitor));
		lista.add(new CertificadoDigitalVO("Tipo de Certificado", tipoCertificado));
		lista.add(new CertificadoDigitalVO("UF do Órgao Expedidor da Identidade", ufOrgaoExpedidorIdentidade));
		lista.add(new CertificadoDigitalVO("UF do Título de Eleitor", ufTituloEleitor));
		lista.add(new CertificadoDigitalVO("Zona do Título de Eleitor", zonaTituloEleitor));
		lista.add(new CertificadoDigitalVO("Válido a partir da Data", DateFormatUtils.format(antesData, "dd/MM/yyyy hh:mm:ss")));		
		lista.add(new CertificadoDigitalVO("Identificador de Chave da Autoridade", identificadorChaveAutoridade));
		lista.add(new CertificadoDigitalVO("Válido até a Data", DateFormatUtils.format(depoisData, "dd/MM/yyyy hh:mm:ss")));
		lista.add(new CertificadoDigitalVO("Autoridade de Certificação", autoridadeCertificacao.toString()));
		lista.add(new CertificadoDigitalVO("Lista de Revogacão de Certificados", listaRevogacaoCertificados.toString()));
		lista.add(new CertificadoDigitalVO("Nome Distinto do Emitente", nomeDistintoEmitente));
		lista.add(new CertificadoDigitalVO("Uso da Chave", usoChave));
		lista.add(new CertificadoDigitalVO("Tamanho do Caminho", tamanhoCaminho.toString()));
		lista.add(new CertificadoDigitalVO("Número Serial", numeroSerial));
		lista.add(new CertificadoDigitalVO("Nome Distinto do Sujeito", nomeDistintoSujeito));
		lista.add(new CertificadoDigitalVO("Identificador de Chave do Sujeito", identificadorChaveSujeito));
		
		return lista;
	}
}
