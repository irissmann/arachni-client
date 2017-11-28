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

package de.irissmann.arachni.client.rest.request;

@SuppressWarnings("unused")
public class HttpParameters {
    
    private final String userAgent = "ArachniRestClient";
    
    private Integer requestTimeout;
    
    private Integer requestRedirectLimit;
    
    private Integer requestConcurrency;
    
    private Integer requestQueueSize;
    
    private Integer responseMaxSize;
    
    public HttpParameters() {
        super();
    }
    
    public HttpParameters setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }

    public HttpParameters setRequestRedirectLimit(int requestRedirectLimit) {
        this.requestRedirectLimit = requestRedirectLimit;
        return this;
    }

    public HttpParameters setRequestConcurrency(int requestConcurrency) {
        this.requestConcurrency = requestConcurrency;
        return this;
    }

    public HttpParameters setRequestQueueSize(int requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
        return this;
    }

    public HttpParameters setResponseMaxSize(int responseMaxSize) {
        this.responseMaxSize = responseMaxSize;
        return this;
    }
}
