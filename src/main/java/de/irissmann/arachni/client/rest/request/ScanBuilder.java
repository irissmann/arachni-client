package de.irissmann.arachni.client.rest.request;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class ScanBuilder {
    Logger log = LoggerFactory.getLogger(ScanBuilder.class);
    
    private URL url;
    
    public ScanBuilder url(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException exception) {
            log.warn(exception.getMessage(), exception);
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
        
        return this;
    }

    public RequestScan build() {
        if (url == null) {
            throw new IllegalArgumentException("URL must not be null.");
        }
        RequestScan scan = new RequestScan(url);
        return scan;
    }
}
