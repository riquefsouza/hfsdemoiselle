package br.hfs.util.json;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import br.hfs.util.correio.Correio;

public class JsonUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("JsonUtil");
	
	private DecimalFormat fmt;

	public JsonUtil(){
		fmt = new DecimalFormat("#0.00");		
	}
	
	public JSONArray lerArquivo(String arquivo) throws JsonException {
		JSONArray vetor = null;
		try {
			String dados = FileUtils.readFileToString(new File(arquivo),
					"UTF-8");
			vetor = new JSONArray(dados);
		} catch (IOException e) {
			throw new JsonException(log, e.getMessage());
		}
		return vetor;
	}

	public JSONObject lerObjetoJSON(JSONArray vetor, int indice) {
		JSONObject jsonObject = vetor.getJSONObject(indice);
		return jsonObject;
	}

	public List<JSONObject> lerArquivoJSON(String arquivo) throws JsonException {
		List<JSONObject> lista = new ArrayList<JSONObject>();
		JSONArray vetor = lerArquivo(arquivo);

		for (int i = 0; i < vetor.length(); i++) {
			lista.add(lerObjetoJSON(vetor, i));
		}

		return lista;
	}

	public Correio fromJSON(Long id, JSONObject jsonObject) {
		Correio co = new Correio();
		co.setId(id);
		co.setUf(jsonObject.get("uf").toString());
		co.setEstado(jsonObject.get("estado").toString());
		co.setUfCep1(jsonObject.get("ufcep1").toString());
		co.setUfCep2(jsonObject.get("ufcep2").toString());
		co.setCidade(jsonObject.get("cidade").toString());
		co.setLogradouro(jsonObject.get("logradouro").toString());
		co.setBairro(jsonObject.get("bairro").toString());
		co.setCep(jsonObject.get("cep").toString());
		co.setTipoLogradouro(jsonObject.get("tp_logradouro").toString());
		return co;
	}

	public void lerArquivoJSON(String arquivo, IJsonUtilListener listener)
			throws JsonException {
		JSONArray vetor = lerArquivo(arquivo);

		int total = vetor.length();
		for (int i = 0; i < total; i++) {
			listener.carregando(total, i + 1, lerObjetoJSON(vetor, i));
		}

	}

	public void carregarPercentual(String arquivo) {
		try {
			int j = 0;
			JSONArray vetor = lerArquivo(arquivo);

			JsonParams params = iniciarParams(vetor);

			for (j = 0; j < 100; j++) {
				params = carregarFracao(vetor, params, params.getParte());
				params.setOrdem(params.getOrdem() + 1);

				// Trazer o resto
				if ((params.getOrdem() + params.getResto()) == params
						.getTotal()) {
					params = carregarFracao(vetor, params, params.getResto());
				}
			}

		} catch (JsonException e) {
			e.printStackTrace();
		}
	}

	public JsonParams iniciarParams(JSONArray vetor) {
		JsonParams params = new JsonParams();
		params.setTotal(vetor.length());
		params.setPercentual(0);
		params.setPercentualInteiro(0);
		params.setOrdem(0);
		params.setProgresso(0);
		params.setParteFracao(0);

		params.setFracao(params.getTotal() * 0.01);
		params.setParte((int) params.getFracao());
		params.setResto(params.getTotal() - (params.getParte() * 100));
		
		params.setCorreio(new ArrayList<Correio>());
		return params;
	}

	public JsonParams carregarFracao(JSONArray vetor, JsonParams params,
			int parteFracao) {
		JSONObject jsonObject;
		Correio co;
		params.setParteFracao(parteFracao);

		int progresso = params.getOrdem() + params.getParteFracao();

		params.getCorreio().clear();
		
		for (int i = params.getOrdem(); i < progresso; i++) {
			jsonObject = lerObjetoJSON(vetor, i);
			co = fromJSON((long)(i + 1), jsonObject);
			params.getCorreio().add(co);

			params.setPercentual(((double) params.getOrdem() / (double) params
					.getTotal()) * 100);
			params.setPercentualInteiro((int) params.getPercentual());
			params.setOrdem(i);
			params.setProgresso(progresso);

			
			System.out.println(params.getProgresso() + " - "
					+ params.getPercentualInteiro() + " - "
					+ fmt.format(params.getPercentual()) + "% - "
					+ params.getParteFracao() + " - " + params.getOrdem()
					+ " - " + co.toString());
			
		}

		return params;
	}

}
