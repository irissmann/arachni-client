package de.irissmann.arachni.client.rest;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import de.irissmann.arachni.client.ArachniClientException;
import de.irissmann.arachni.client.ArachniClient;
import de.irissmann.arachni.client.rest.request.RequestScan;
import de.irissmann.arachni.client.rest.response.ResponseScan;

public class ArachniApiRestImpl implements ArachniClient {
    
    public final static String PATH_SCANS = "scans";

    private ArachniRestClient restClient;
    
    private Gson gson;
    
    protected ArachniApiRestImpl() {
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public List<String> getScans() throws ArachniClientException {
        String json;
        json = restClient.get(PATH_SCANS);
        Map<String, JsonObject> scans = gson.fromJson(json, Map.class);
        return new ArrayList<String>(scans.keySet());
    }
    
    public String performScan(RequestScan scan) throws ArachniClientException {
        String body = gson.toJson(scan);
        String json = restClient.post(PATH_SCANS, body);
        Map<String, String> response = gson.fromJson(json, Map.class);
        return response.get("id");
    }

    public ResponseScan monitorScan(String id) throws ArachniClientException {
        String json = restClient.get(String.join("/", PATH_SCANS, id));
        return gson.fromJson(json, ResponseScan.class);
    }
    
    public boolean shutdownScan(String id) throws ArachniClientException {
        return restClient.delete(String.join("/", PATH_SCANS, id));
    }
    
    public String getScanReportJson(String id) throws ArachniClientException {
        return restClient.get(String.join("/", PATH_SCANS, id, "report.json"));
    }
    
    public void getScanReportHtml(String id, OutputStream outstream) throws ArachniClientException {
        restClient.getBinaryContent(String.join("/", PATH_SCANS, id, "report.html.zip"), outstream);
    }

    public void close() {
        restClient.close();
    }
    
    protected void setRestClient(ArachniRestClient restClient) {
        this.restClient = restClient;
    }
}
