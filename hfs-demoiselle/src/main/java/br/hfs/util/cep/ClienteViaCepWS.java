package br.hfs.util.cep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteViaCepWS {

	public static String buscarCep(String cep) {
		String json;

		try {

			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					"bravo.trtrio.gov.br", 8080));

			Authenticator authenticator = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return (new PasswordAuthentication("henrique.souza",
							"senha".toCharArray()));
				}
			};
			Authenticator.setDefault(authenticator);
			
			URL url = new URL("http://viacep.com.br/ws/" + cep + "/json");
			URLConnection urlConnection = url.openConnection(proxy);
			InputStream is = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringBuilder jsonSb = new StringBuilder();

			String line = null;
			while ((line = br.readLine()) != null) {
				jsonSb.append(line.trim());
			}

			json = jsonSb.toString();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return json;
	}

	public static void main(String[] args) throws IOException {
		String json = buscarCep("20541090");
		System.out.println(json);

		Map<String, String> mapa = new HashMap<String, String>();

		Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
		while (matcher.find()) {
			String[] group = matcher.group().split(":");
			mapa.put(group[0].replaceAll("\"", "").trim(),
					group[1].replaceAll("\"", "").trim());
		}

		System.out.println(mapa);
	}
}
