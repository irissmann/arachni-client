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

public class HttpParametersBuilder {
    
    private Integer requestTimeout;
    
    private Integer requestRedirectLimit;
    
    private Integer requestConcurrency;
    
    private Integer requestQueueSize;
    
    private Integer responseMaxSize;
    
    HttpParametersBuilder() {
        super();
    }
    
    public HttpParametersBuilder requestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }
    
    public HttpParametersBuilder requestRedirectLimit(int requestRedirectLimit) {
        this.requestRedirectLimit = requestRedirectLimit;
        return this;
    }
    
    public HttpParametersBuilder requestConcurrency(int requestConcurrency) {
        this.requestConcurrency = requestConcurrency;
        return this;
    }
    
    public HttpParametersBuilder requestQueueSize(int requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
        return this;
    }
    
    public HttpParametersBuilder responseMaxSize(int responseMaxSize) {
        this.responseMaxSize = responseMaxSize;
        return this;
    }
    
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
