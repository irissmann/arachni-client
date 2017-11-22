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

package de.irissmann.arachni.client.rest;

import java.net.URL;

import org.apache.http.auth.UsernamePasswordCredentials;

import de.irissmann.arachni.client.ArachniClient;

public class ArachniRestClientBuilder {

    private final URL arachniRestUrl;
    private UsernamePasswordCredentials credentials;
    

    private ArachniRestClientBuilder(URL arachniRestUrl) {
        this.arachniRestUrl = arachniRestUrl;
    }

    public static ArachniRestClientBuilder create(URL arachniRestUrl) {
        return new ArachniRestClientBuilder(arachniRestUrl);
    }

    public final ArachniRestClientBuilder addCredentials(String username, String password) {
        this.credentials = new UsernamePasswordCredentials(username, password);
        return this;
    }
    
    public final ArachniClient build() {
        return new ArachniRestClient(arachniRestUrl, credentials);
    }
}
