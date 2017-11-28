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

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ScanBuilder {
    private static final Logger log = LoggerFactory.getLogger(ScanBuilder.class);
    
    private URL url;
    
    private Scope scope;
    
    private HttpParameters requestHttp;
    
    public ScanBuilder url(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException exception) {
            log.info(exception.getMessage(), exception);
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
        
        return this;
    }
    
    public ScanBuilder scope(Scope scope) {
        this.scope = scope;
        return this;
    }
    
    public ScanBuilder http(HttpParameters requestHttp) {
        this.requestHttp = requestHttp;
        return this;
    }

    public ScanRequest build() {
        if (url == null) {
            log.info("No URL specified.");
            throw new IllegalArgumentException("URL must not be null.");
        }
        ScanRequest scanRequest = new ScanRequest(url);
        
        if (scope != null) {
            log.debug("Set scope.");
            scanRequest.setScope(scope);
        }
        
        if (requestHttp != null) {
            log.debug("Set http options.");
            scanRequest.setHttp(requestHttp);
        }
        return scanRequest;
    }
}
