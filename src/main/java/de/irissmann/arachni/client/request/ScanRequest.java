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

import java.net.URL;
import java.util.List;

/**
 * A {@code ScanRequest} is needed to perform a new scan. Use the {@code #create()} method to get a 
 * {@code ScanRequestBuilder}.
 * 
 * @author Ingo Rissmann
 * @since 1.0.0
 * @see ScanRequestBuilder
 */
@SuppressWarnings("unused")
public class ScanRequest {

    private final URL url;
    
    private HttpParameters http;
    
    private Scope scope;
    
    private List<String> checks;
    
    ScanRequest(URL url) {
        this.url = url;
    }

    void setHttp(HttpParameters http) {
        this.http = http;
    }
    
    void setScope(Scope scope) {
        this.scope = scope;
    }
    
    void setChecks(List<String> checks) {
        this.checks = checks;
    }
    
    public static final ScanRequestBuilder create() {
        return new ScanRequestBuilder();
    }
}
