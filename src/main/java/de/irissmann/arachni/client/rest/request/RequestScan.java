package de.irissmann.arachni.client.rest.request;

@SuppressWarnings("unused")
public class RequestScan {

    private final String url;
    
    private RequestHttp http;
    
    public RequestScan(String url) {
        this.url = url;
    }

    public void setHttp(RequestHttp http) {
        this.http = http;
    }
}
