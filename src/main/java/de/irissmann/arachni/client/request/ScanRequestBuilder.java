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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this builder to create a {@code ScanRequest}. To get an instance of this builder call
 * {@link ScanRequest#create()}.
 * 
 * @author Ingo Rissmann
 * @since 1.0.0
 */
public final class ScanRequestBuilder {
    private static final Logger log = LoggerFactory.getLogger(ScanRequestBuilder.class);
    
    private URL url;
    
    private Scope scope;
    
    private HttpParameters requestHttp;
    
    private List<String> checks;
    
    /**
     * Sets the URL of the site to scan. The URL is mandatory to build a {@code ScanRequest}.
     * 
     * @param url String with the URL to scan.
     * @throws IllegalArgumentException If URL is invalid.
     * @return This builder instance.
     */
    public ScanRequestBuilder url(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException exception) {
            log.info(exception.getMessage(), exception);
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
        return this;
    }
    
    /**
     * Sets the scope for the site under scan.
     * 
     * @param scope The scope object.
     * @return This builder instance.
     */
    public ScanRequestBuilder scope(Scope scope) {
        this.scope = scope;
        return this;
    }
    
    /**
     * Sets the HTTP parameters for the site under scan.
     * 
     * @param requestHttp The HTTP parameters.
     * @return This builder instance.
     */
    public ScanRequestBuilder http(HttpParameters requestHttp) {
        this.requestHttp = requestHttp;
        return this;
    }
    
    /**
     * Adds a check.
     * 
     * @param check The check to add.
     * @return This builder instance.
     */
    public ScanRequestBuilder addCheck(String check) {
        if (StringUtils.isEmpty(check)) {
            log.info("Check is empty and will not be added.");
            return this;
        }
        if (checks == null) {
            checks = new ArrayList<String>();
        }
        checks.add(check);
        return this;
    }

    /**
     * Checks the mandatory parameters and creates the {@code ScanRequest}.
     * 
     * @return A {@code ScanRequest}.
     * @throws IllegalArgumentException If URL is empty.
     */
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
        
        if (checks != null) {
            log.debug("Set checks.");
            scanRequest.setChecks(checks);
        }
        
        return scanRequest;
    }
}
