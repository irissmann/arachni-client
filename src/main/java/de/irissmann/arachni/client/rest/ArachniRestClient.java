package de.irissmann.arachni.client.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import de.irissmann.arachni.api.ArachniApiException;

public class ArachniRestClient {
	private final HttpClient httpClient;
	
	private final URL baseUrl;
	
	public ArachniRestClient(URL baseUrl) {
		this.baseUrl = baseUrl;
		httpClient = HttpClientBuilder.create().build();
	}
	
	public ArachniRestClient(URL baseUrl, UsernamePasswordCredentials credentials) {
	    this.baseUrl = baseUrl;
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
        httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
	}
	
	public String get(String path) throws ArachniApiException {
	    try {
		HttpGet getRequest = new HttpGet(new URL(baseUrl, path).toString());
		HttpResponse response = httpClient.execute(getRequest);
		return EntityUtils.toString(response.getEntity());
	    } catch (MalformedURLException exception) {
	        throw new ArachniApiException("URL not valid.", exception);
	    } catch (IOException exception) {
	        throw new ArachniApiException("Could not connect to server.", exception);
	    }
	}
}
