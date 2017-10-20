package de.irissmann.arachni.api.scans;

public class Scan {

    private final String url;
    
    private Http http;
    
    public Scan(String url) {
        this.url = url;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public String getUrl() {
        return url;
    }

}
