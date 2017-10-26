package de.irissmann.arachni.api.scans;

@SuppressWarnings("unused")
public class Scan {

    private final String url;
    
    private Http http;
    
    public Scan(String url) {
        this.url = url;
    }

    public void setHttp(Http http) {
        this.http = http;
    }
}
