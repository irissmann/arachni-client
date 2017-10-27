package de.irissmann.arachni.client.rest.response;

import java.lang.reflect.Array;
import java.util.List;

public class ResponseScan {
    
    private boolean busy;
    
    private String status;
    
    private String seed;
    
    private List errors;
    
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
}
