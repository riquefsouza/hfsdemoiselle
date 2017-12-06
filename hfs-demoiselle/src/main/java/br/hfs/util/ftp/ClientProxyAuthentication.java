package br.hfs.util.ftp;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ClientProxyAuthentication {

	public static void main(String[] args) throws Exception {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("bravo.trtrio.gov.br", 8080),
				new UsernamePasswordCredentials("henrique.souza", ""));
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider).build();
		try {
			HttpHost target = new HttpHost("www.trt1.jus.br", 80, "http");
			HttpHost proxy = new HttpHost("bravo.trtrio.gov.br", 8080);

			RequestConfig config = RequestConfig.custom().setProxy(proxy)
					.build();
			HttpGet httpget = new HttpGet("/basic-auth/user/passwd");
			httpget.setConfig(config);

			System.out.println("Executando " + httpget.getRequestLine()
					+ " to " + target + " via " + proxy);

			CloseableHttpResponse response = httpclient
					.execute(target, httpget);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				System.out.println(EntityUtils.toString(response.getEntity()));
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}
}
