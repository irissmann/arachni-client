package de.irissmann.arachni.client.rest;

import java.io.OutputStream;

import de.irissmann.arachni.client.ArachniClientException;
import de.irissmann.arachni.client.Scan;
import de.irissmann.arachni.client.rest.response.ResponseScan;

public class ScanRestImpl extends Scan {
    
    private ArachniRestClient restClient;
    
    public ScanRestImpl(String id, ArachniRestClient restClient) {
        super(id);
        this.restClient = restClient;
    }

    @Override
    public ResponseScan monitor() throws ArachniClientException {
        return restClient.monitor(getId());
    }

    @Override
    public boolean shutdown() throws ArachniClientException {
        return restClient.shutdownScan(getId());
    }

    @Override
    public String getReportJson() throws ArachniClientException {
        return restClient.getScanReportJson(getId());
    }

    @Override
    public void getReportHtml(OutputStream outstream) throws ArachniClientException {
        restClient.getScanReportHtml(getId(), outstream);
    }

}
