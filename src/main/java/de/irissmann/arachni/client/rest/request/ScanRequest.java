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

import java.net.URL;

@SuppressWarnings("unused")
public class ScanRequest {

    private final URL url;
    
    private RequestHttp http;
    
    private Scope scope;
    
    ScanRequest(URL url) {
        this.url = url;
    }

    public void setHttp(RequestHttp http) {
        this.http = http;
    }
    
    void setScope(Scope scope) {
        this.scope = scope;
    }
    
    public static final ScanBuilder create() {
        return new ScanBuilder();
    }
}
