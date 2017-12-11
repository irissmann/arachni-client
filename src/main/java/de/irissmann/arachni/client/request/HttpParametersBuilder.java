/*
 * Copyright 2017 Ingo Rissmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.irissmann.arachni.client.request;

/**
 * This is a builder to create an instance of {@link HttpParameters}. To get an instance of this builder call 
 * {@link HttpParameters#create()}.
 * <div>
 * For default values see also the Arachni wiki page.
 * </div>
 * 
 * @author Ingo Rissmann
 */
public class HttpParametersBuilder {
    
    private Integer requestTimeout;
    
    private Integer requestRedirectLimit;
    
    private Integer requestConcurrency;
    
    private Integer requestQueueSize;
    
    private Integer responseMaxSize;
    
    HttpParametersBuilder() {
        super();
    }
    
    /**
     * Limit how long the client should wait for a response from the server.
     * 
     * @param requestTimeout Timeout in milliseconds
     * @return This builder instance.
     */
    public HttpParametersBuilder requestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }
    
    /**
     * Limits the amount of redirects the client should follow for each request.
     * 
     * @param requestRedirectLimit Amount of redirects
     * @return This builder instance.
     */
    public HttpParametersBuilder requestRedirectLimit(int requestRedirectLimit) {
        this.requestRedirectLimit = requestRedirectLimit;
        return this;
    }
    
    /**
     * Sets the maximum amount of requests to be active at any given time; this usually directly translates to the 
     * amount of open connections.
     * 
     * @param requestConcurrency Amount of requests.
     * @return This builder instance.
     */
    public HttpParametersBuilder requestConcurrency(int requestConcurrency) {
        this.requestConcurrency = requestConcurrency;
        return this;
    }
    
    /**
     * Maximum amount of requests to keep in the client queue.
     * 
     * @param requestQueueSize Amount of requests.
     * @return This builder instance.
     */
    public HttpParametersBuilder requestQueueSize(int requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
        return this;
    }
    
    /**
     * Limits the size of response bodies the client accepts. Essentially, the client will not download bodies of 
     * responses which have a Content-Length larger than the specified value.
     * 
     * @param responseMaxSize Max size of response bodies.
     * @return This builder instance.
     */
    public HttpParametersBuilder responseMaxSize(int responseMaxSize) {
        this.responseMaxSize = responseMaxSize;
        return this;
    }
    
    /**
     * Returns an instance of {@code HttpParameters} to change the default values for a new scan.
     * 
     * @return A {@code HttpParameters} instance.
     */
    public HttpParameters build() {
        HttpParameters parameters = new HttpParameters();
        
        if (requestTimeout != null) {
            parameters.setRequestTimeout(requestTimeout);
        }
        
        if (requestRedirectLimit != null) {
            parameters.setRequestRedirectLimit(requestRedirectLimit);
        }
        
        if (requestConcurrency != null) {
            parameters.setRequestConcurrency(requestConcurrency);
        }
        
        if (requestQueueSize != null) {
            parameters.setRequestQueueSize(requestQueueSize);
        }
        
        if (responseMaxSize != null) {
            parameters.setResponseMaxSize(responseMaxSize);
        }
        return parameters;
    }

}
