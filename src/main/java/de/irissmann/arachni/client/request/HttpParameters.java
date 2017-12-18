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
 * Defines Http parameter settings for scan request.
 * 
 * @author Ingo Rissmann
 * @since 1.0.0
 * @see HttpParametersBuilder
 */
@SuppressWarnings("unused")
public class HttpParameters {
    
    private final String userAgent = "ArachniRestClient";
    
    private Integer requestTimeout;
    
    private Integer requestRedirectLimit;
    
    private Integer requestConcurrency;
    
    private Integer requestQueueSize;
    
    private Integer responseMaxSize;
    
    HttpParameters() {
        super();
    }
    
    HttpParameters setRequestTimeout(Integer requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }

    HttpParameters setRequestRedirectLimit(Integer requestRedirectLimit) {
        this.requestRedirectLimit = requestRedirectLimit;
        return this;
    }

    HttpParameters setRequestConcurrency(Integer requestConcurrency) {
        this.requestConcurrency = requestConcurrency;
        return this;
    }

    HttpParameters setRequestQueueSize(Integer requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
        return this;
    }

    HttpParameters setResponseMaxSize(Integer responseMaxSize) {
        this.responseMaxSize = responseMaxSize;
        return this;
    }
    
    /**
     * Returns a builder to create a {@code HttpParameters} object.
     * 
     * @return The {@code HttpParametersBuilder}
     */
    public static final HttpParametersBuilder create() {
        return new HttpParametersBuilder();
    }
}
