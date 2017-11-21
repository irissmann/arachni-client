package de.irissmann.arachni.client.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.irissmann.arachni.client.ArachniApiException;

public class ArachniRestClient {

    public static final Logger log = LoggerFactory.getLogger(ArachniRestClient.class);

    private final CloseableHttpClient httpClient;

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
        HttpGet getRequest = new HttpGet(getUri(path));
        try {
            HttpResponse response = httpClient.execute(getRequest);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException exception) {
            throw new ArachniApiException("Could not connect to server.", exception);
        } finally {
            getRequest.reset();
        }
    }

    public void getBinaryContent(String path, OutputStream outstream) throws ArachniApiException {
        HttpGet getRequest = new HttpGet(getUri(path));
        try {
            HttpResponse response = httpClient.execute(getRequest);
            response.getEntity().writeTo(outstream);
        } catch (IOException exception) {
            throw new ArachniApiException("Could not connect to server.", exception);
        } finally {
            getRequest.reset();
        }
    }

    public String post(String path, String body) throws ArachniApiException {
        log.debug("POST request to path {} with json: {}", path, body);
        HttpPost postRequest = new HttpPost(getUri(path));
        try {
            HttpEntity entity = new StringEntity(body);
            postRequest.setEntity(entity);
            postRequest.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
            postRequest.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
            HttpResponse response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                String message = EntityUtils.toString(response.getEntity());
                throw new ArachniApiException(message);
            }
            return EntityUtils.toString(response.getEntity());
        } catch (IOException exception) {
            throw new ArachniApiException("Could not connect to server.", exception);
        } finally {
            postRequest.reset();
        }
    }

    public boolean delete(String path) throws ArachniApiException {
        HttpDelete deleteRequest = new HttpDelete(getUri(path));
        try {
            HttpResponse response = httpClient.execute(deleteRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return true;
            } else {
                String message = EntityUtils.toString(response.getEntity());
                throw new ArachniApiException(message);
            }
        } catch (IOException exception) {
            throw new ArachniApiException("Could not connect to server.", exception);
        } finally {
            deleteRequest.reset();
        }
    }

    private URI getUri(String path) throws ArachniApiException {
        try {
            return new URL(baseUrl, path).toURI();
        } catch (Exception exception) {
            throw new ArachniApiException("URL not valid.", exception);
        }
    }

    protected void close() {
        log.info("Try to close http connection.");
        try {
            httpClient.close();
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }
}
