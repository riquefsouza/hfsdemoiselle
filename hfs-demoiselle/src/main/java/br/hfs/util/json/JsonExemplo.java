package br.hfs.util.json;

import java.text.DecimalFormat;
import java.util.List;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import br.hfs.util.correio.Correio;

public class JsonExemplo implements IJsonUtilListener {
	static JsonUtil ju = new JsonUtil();
	DecimalFormat fmt = new DecimalFormat("#0.00");
	
	public static void main(String[] args) {
		//JsonExemplo jo = new JsonExemplo();	
		//jo.carregar(jo);
		//jo.carregar3();

		ju.carregarPercentual("C:/Demoiselle/workspace/hfs-demoiselle/correios/correios.json");
		
	}

	
	public void carregar(IJsonUtilListener listener){	
		try {
			String arquivo = "C:/Demoiselle/workspace/hfs-demoiselle/correios/correios.json";			
			ju.lerArquivoJSON(arquivo, listener);
		} catch (JsonException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void carregando(long total, long ordem, JSONObject jsonObject) {
		Correio co = ju.fromJSON(ordem, jsonObject);		
		double percentual = ((double)ordem/(double)total)*100;
		System.out.println(fmt.format(percentual) + "% - "+co.toString());
	}

	public void carregar2(){	
		try {
			String arquivo = "C:/Demoiselle/workspace/hfs-demoiselle/correios/correios.json";			
			List<JSONObject> lista = ju.lerArquivoJSON(arquivo);

			Correio co;
			long i = 1;
			for (JSONObject item : lista) {
				co = ju.fromJSON(i, item);
				i++;
				System.out.println(co.toString());
			}
		} catch (JsonException e) {
			e.printStackTrace();
		}

	}

	public void carregar3(){	
		try {
			String arquivo = "C:/Demoiselle/workspace/hfs-demoiselle/correios/correios.json";			
			JSONArray vetor = ju.lerArquivo(arquivo);
			int total = vetor.length();
			int ordem = 1;
			double percentual = 0;
			JSONObject jsonObject;
			Correio co;
			Integer progresso = 0;
			
			for (ordem = 0; ordem < total; ordem++) {
				percentual = ((double)ordem/(double)total)*100;
				
				progresso = progresso + (int) percentual;
				
				jsonObject = ju.lerObjetoJSON(vetor, progresso.intValue());	
				co = ju.fromJSON(progresso.longValue(), jsonObject);
				System.out.println(total + " - " + ordem + " - " + progresso.intValue() +" - " + percentual + " - " +   fmt.format(percentual) + "% - "+co.toString());
			}
		} catch (JsonException e) {
			e.printStackTrace();
		}

	}

	
}
