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

final public class ScanBuilder {
    Logger log = LoggerFactory.getLogger(ScanBuilder.class);
    
    private URL url;
    
    public ScanBuilder url(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException exception) {
            log.warn(exception.getMessage(), exception);
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
        
        return this;
    }

    public ScanRequest build() {
        if (url == null) {
            throw new IllegalArgumentException("URL must not be null.");
        }
        ScanRequest scan = new ScanRequest(url);
        return scan;
    }
}
