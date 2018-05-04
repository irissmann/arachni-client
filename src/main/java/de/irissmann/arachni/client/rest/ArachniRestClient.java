/*
 * Copyright 2017 Ingo Rissmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.irissmann.arachni.client.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.irissmann.arachni.client.ArachniClient;
import de.irissmann.arachni.client.ArachniClientException;
import de.irissmann.arachni.client.Scan;
import de.irissmann.arachni.client.request.ScanRequest;
import de.irissmann.arachni.client.response.ScanResponse;
import de.irissmann.arachni.client.rest.ArachniUtils.MergeConflictStrategy;

/**
 * Implementation that use the Arachni REST interface.
 * 
 * @author Ingo Rissmann
 * @since 1.0.0
 */
public class ArachniRestClient implements ArachniClient {

    private static final Logger log = LoggerFactory.getLogger(ArachniRestClient.class);

    public final static String PATH_SCANS = "scans";

    private final CloseableHttpClient httpClient;

    private final URL baseUrl;

    private Gson gson;
    
    private final MergeConflictStrategy mergeConflictStrategy;

    ArachniRestClient(URL baseUrl, UsernamePasswordCredentials credentials, MergeConflictStrategy mergeConflictStrategy) {
        this.baseUrl = baseUrl;
        this.mergeConflictStrategy = mergeConflictStrategy;
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        if (credentials == null) {
            httpClient = HttpClientBuilder.create().build();
        } else {
            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, credentials);
            httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        }
    }
    
    /* (non-Javadoc)
     * @see de.irissmann.arachni.client.ArachniClient#performScan(de.irissmann.arachni.client.request.ScanRequest)
     */
    public Scan performScan(ScanRequest scanRequest) throws ArachniClientException {
        return performScan(scanRequest, null);
    }
    
    /* (non-Javadoc)
     * @see de.irissmann.arachni.client.ArachniClient#performScan(de.irissmann.arachni.client.request.ScanRequest, java.lang.String)
     */
    public Scan performScan(ScanRequest scanRequest, String mergeString) {
        String body;
        if (StringUtils.isNotBlank(mergeString)) {
            JsonParser parser = new JsonParser();
            JsonObject scan = gson.toJsonTree(scanRequest).getAsJsonObject();
            JsonObject mergeObject = gson.toJsonTree(parser.parse(mergeString)).getAsJsonObject();
            ArachniUtils.merge(scan, mergeObject, mergeConflictStrategy);
            body = scan.toString();
        } else {
            body = gson.toJson(scanRequest);
        }
        String json = post(PATH_SCANS, body);
        Map<String, String> response = gson.fromJson(json, Map.class);
        return new ScanRestImpl(response.get("id"), this);
    }

    /* (non-Javadoc)
     * @see de.irissmann.arachni.client.ArachniClient#getScans()
     */
    public List<String> getScans() {
        String json;
        json = get(PATH_SCANS);
        Map<String, JsonObject> scans = gson.fromJson(json, Map.class);
        return new ArrayList<String>(scans.keySet());
    }
    
    ScanResponse monitor(String id) {
        String json = get(String.join("/", PATH_SCANS, id));
        return gson.fromJson(json, ScanResponse.class);
    }

    boolean shutdownScan(String id) {
        return delete(String.join("/", PATH_SCANS, id));
    }

    String getScanReportJson(String id) {
        return get(String.join("/", PATH_SCANS, id, "report.json"));
    }
    
    void getScanReportHtml(String id, OutputStream outstream) {
        getBinaryContent(String.join("/", PATH_SCANS, id, "report.html.zip"), outstream);
    }

    private String get(String path) {
        HttpGet getRequest = new HttpGet(getUri(path));
        try {
            HttpResponse response = httpClient.execute(getRequest);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException exception) {
            throw new ArachniClientException("Could not connect to server.", exception);
        } finally {
            getRequest.reset();
        }
    }

    private void getBinaryContent(String path, OutputStream outstream) {
        HttpGet getRequest = new HttpGet(getUri(path));
        try {
            HttpResponse response = httpClient.execute(getRequest);
            response.getEntity().writeTo(outstream);
        } catch (IOException exception) {
            throw new ArachniClientException("Could not connect to server.", exception);
        } finally {
            getRequest.reset();
        }
    }

    private String post(String path, String body) {
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
                throw new ArachniClientException(message);
            }
            return EntityUtils.toString(response.getEntity());
        } catch (IOException exception) {
            throw new ArachniClientException("Could not connect to server.", exception);
        } finally {
            postRequest.reset();
        }
    }

    private boolean delete(String path) {
        HttpDelete deleteRequest = new HttpDelete(getUri(path));
        try {
            HttpResponse response = httpClient.execute(deleteRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return true;
            } else {
                String message = EntityUtils.toString(response.getEntity());
                throw new ArachniClientException(message);
            }
        } catch (IOException exception) {
            throw new ArachniClientException("Could not connect to server.", exception);
        } finally {
            deleteRequest.reset();
        }
    }

    private URI getUri(String path) {
        try {
            return new URL(baseUrl, path).toURI();
        } catch (Exception exception) {
            throw new ArachniClientException("URL not valid.", exception);
        }
    }

    /* (non-Javadoc)
     * @see de.irissmann.arachni.client.ArachniClient#close()
     */
    public void close() {
        log.info("Try to close http connection.");
        try {
            httpClient.close();
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
            throw new ArachniClientException(exception.getMessage());
        }
    }
}
