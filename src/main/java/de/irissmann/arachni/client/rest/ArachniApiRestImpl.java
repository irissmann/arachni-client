package de.irissmann.arachni.client.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import de.irissmann.arachni.api.ArachniApi;
import de.irissmann.arachni.api.ArachniApiException;

public class ArachniApiRestImpl implements ArachniApi {

    private ArachniRestClient restClient;

    public List<String> getScans() throws ArachniApiException {
        String json;
        json = restClient.get("/scans");
        Gson gson = new Gson();
        Map<String, JsonObject> scans = gson.fromJson(json, Map.class);
        return new ArrayList<String>(scans.keySet());
    }

    protected void setRestClient(ArachniRestClient restClient) {
        this.restClient = restClient;
    }
}
