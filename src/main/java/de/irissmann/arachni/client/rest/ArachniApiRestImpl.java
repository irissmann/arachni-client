package de.irissmann.arachni.client.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import de.irissmann.arachni.api.ArachniApi;
import de.irissmann.arachni.api.ArachniApiException;
import de.irissmann.arachni.client.rest.request.RequestScan;
import de.irissmann.arachni.client.rest.response.ResponseScan;

public class ArachniApiRestImpl implements ArachniApi {

    private ArachniRestClient restClient;
    
    private Gson gson;
    
    protected ArachniApiRestImpl() {
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public List<String> getScans() throws ArachniApiException {
        String json;
        json = restClient.get("scans");
        Map<String, JsonObject> scans = gson.fromJson(json, Map.class);
        return new ArrayList<String>(scans.keySet());
    }
    
    public String performScan(RequestScan scan) throws ArachniApiException {
        String body = gson.toJson(scan);
        String json = restClient.post("scans", body);
        Map<String, String> response = gson.fromJson(json, Map.class);
        return response.get("id");
    }

    public ResponseScan monitorScan(String id) throws ArachniApiException {
        String json = restClient.get(String.join("/", "scans", id));
        ResponseScan scan = gson.fromJson(json, ResponseScan.class);
        return scan;
    }
    
    public boolean shutdownScan(String id) throws ArachniApiException {
        return restClient.delete(String.join("/", "scans", id));
    }
    
    public String getScanReportJson(String id) throws ArachniApiException {
        return restClient.get(String.join("/", "scans", id, "report.json"));
    }

    protected void setRestClient(ArachniRestClient restClient) {
        this.restClient = restClient;
    }
}
