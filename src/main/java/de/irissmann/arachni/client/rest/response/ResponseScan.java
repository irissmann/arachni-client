package de.irissmann.arachni.client.rest.response;

import java.util.List;

public class ResponseScan {
    
    private boolean busy;
    
    private String status;
    
    private String seed;
    
    private List<String> errors;
    
    private Statistics statistics;
    
    public boolean isBusy() {
        return busy;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getSeed() {
        return seed;
    }
    
    public List<?> getErrors() {
        return errors;
    }
    
    public Statistics getStatistics() {
        return statistics;
    }
}
