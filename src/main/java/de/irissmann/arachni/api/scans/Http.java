package de.irissmann.arachni.api.scans;

public class Http {
    
    private final String userAgent = "ArachniRestClient";
    
    private Integer requestTimeout;
    
    private Integer requestRedirectLimit;
    
    private Integer requestConcurrency;
    
    private Integer requestQueueSize;
    
    private Integer responseMaxSize;
    
    public Http() {
        super();
    }
    
    public String getUserAgent() {
        return userAgent;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public Http setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }

    public int getRequestRedirectLimit() {
        return requestRedirectLimit;
    }

    public Http setRequestRedirectLimit(int requestRedirectLimit) {
        this.requestRedirectLimit = requestRedirectLimit;
        return this;
    }

    public int getRequestConcurrency() {
        return requestConcurrency;
    }

    public Http setRequestConcurrency(int requestConcurrency) {
        this.requestConcurrency = requestConcurrency;
        return this;
    }

    public int getRequestQueueSize() {
        return requestQueueSize;
    }

    public Http setRequestQueueSize(int requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
        return this;
    }

    public int getResponseMaxSize() {
        return responseMaxSize;
    }

    public Http setResponseMaxSize(int responseMaxSize) {
        this.responseMaxSize = responseMaxSize;
        return this;
    }
}
