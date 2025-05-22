package net.clush.search.opensearch.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;

import net.clush.search.opensearch.OpenSearch;

public class Client extends OpenSearch{

	public Client(String user, String password, String url) {
		super(user, password, url);
		// TODO Auto-generated constructor stub
	}

	/*
	 * OpenSearch 연결
	 */
	public RestHighLevelClient Connection() {
		try {
			// URL에서 호스트와 포트 추출
			String[] urlParts = URL.replace("http://", "").replace("https://", "").split(":");
			String host = urlParts[0];
			int port = urlParts.length > 1 ? Integer.parseInt(urlParts[1]) : (isHttps ? 443 : 80);
				
			
			final CredentialsProvider  credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials(USER, PASSWORD));
			
			RestClientBuilder builder = RestClient.builder(
						new HttpHost(host,port, isHttps ? "https" : "http")
					).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
						@Override
						public HttpAsyncClientBuilder customizeHttpClient(
								HttpAsyncClientBuilder httpClientBuilder) {
							return httpClientBuilder
									.setDefaultCredentialsProvider(credentialsProvider);
						}
					});
			
			RestHighLevelClient client = new RestHighLevelClient(builder);
			client.ping(RequestOptions.DEFAULT);
			
			return client;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
