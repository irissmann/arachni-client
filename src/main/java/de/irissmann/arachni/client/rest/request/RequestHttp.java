package de.irissmann.arachni.client.rest.request;

@SuppressWarnings("unused")
public class RequestHttp {
    
    private final String userAgent = "ArachniRestClient";
    
    private Integer requestTimeout;
    
    private Integer requestRedirectLimit;
    
    private Integer requestConcurrency;
    
    private Integer requestQueueSize;
    
    private Integer responseMaxSize;
    
    public RequestHttp() {
        super();
    }
    
    public String getUserAgent() {
        return userAgent;
    }

    public RequestHttp setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }

    public RequestHttp setRequestRedirectLimit(int requestRedirectLimit) {
        this.requestRedirectLimit = requestRedirectLimit;
        return this;
    }

    public RequestHttp setRequestConcurrency(int requestConcurrency) {
        this.requestConcurrency = requestConcurrency;
        return this;
    }

    public RequestHttp setRequestQueueSize(int requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
        return this;
    }

    public RequestHttp setResponseMaxSize(int responseMaxSize) {
        this.responseMaxSize = responseMaxSize;
        return this;
    }
}
