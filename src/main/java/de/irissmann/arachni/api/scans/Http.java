package de.irissmann.arachni.api.scans;

import java.util.List;
import java.util.Map;

public class Http {
    
    private String userAgent = "ArachniRestClient";
    
    private Integer requestTimeout;
    
    private Integer requestRedirectLimit;
    
    private Integer requestConcurrency;
    
    private Integer requestQueueSize;
    
    private Map<String, String> requestHeaders;
    
    private Integer responseMaxSize;
    
    private Map<String, String> cookies;

    public Http() {
        super();
    }
    
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getRequestRedirectLimit() {
        return requestRedirectLimit;
    }

    public void setRequestRedirectLimit(int requestRedirectLimit) {
        this.requestRedirectLimit = requestRedirectLimit;
    }

    public int getRequestConcurrency() {
        return requestConcurrency;
    }

    public void setRequestConcurrency(int requestConcurrency) {
        this.requestConcurrency = requestConcurrency;
    }

    public int getRequestQueueSize() {
        return requestQueueSize;
    }

    public void setRequestQueueSize(int requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public int getResponseMaxSize() {
        return responseMaxSize;
    }

    public void setResponseMaxSize(int responseMaxSize) {
        this.responseMaxSize = responseMaxSize;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

}
