package de.irissmann.arachni.client;

import java.io.OutputStream;

import de.irissmann.arachni.client.rest.response.ResponseScan;

public abstract class Scan {
    
    private String id;
    
    public Scan(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public abstract ResponseScan monitor() throws ArachniClientException;

    public abstract boolean shutdown() throws ArachniClientException;

    public abstract String getReportJson() throws ArachniClientException;

    public abstract void getReportHtml(OutputStream outstream) throws ArachniClientException;
    
}
