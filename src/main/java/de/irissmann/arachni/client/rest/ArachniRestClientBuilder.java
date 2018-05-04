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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.auth.UsernamePasswordCredentials;

import de.irissmann.arachni.client.ArachniClient;
import de.irissmann.arachni.client.ArachniClientException;
import de.irissmann.arachni.client.rest.ArachniUtils.MergeConflictStrategy;

/**
 * Builder to get an instance of the {@code ArachniRestClient} implementation. Use the {@code #create(String)}
 * method like in this example:
 * <pre>
 * {@code
 * ArachniClient arachniClient = ArachniRestClientBuilder
 *    .create("http://127.0.0.1:8080")
 *    .build();
 * }
 * </pre>
 * 
 * @author Ingo Rissmann
 * @since 1.0.0
 *
 */
public class ArachniRestClientBuilder {

    private final URL arachniRestUrl;
    private UsernamePasswordCredentials credentials;
    private MergeConflictStrategy mergeConflictStrategy = MergeConflictStrategy.PREFER_OBJECT;

    private ArachniRestClientBuilder(URL arachniRestUrl) {
        this.arachniRestUrl = arachniRestUrl;
    }

    /**
     * Returns an instance of this class.
     * 
     * @param arachniRestUrl The URL of the Arachni REST Server
     * @return A builder.
     * @throws ArachniClientException If the URL is malformed.
     */
    public static ArachniRestClientBuilder create(String arachniRestUrl) {
        try {
            return new ArachniRestClientBuilder(new URL(arachniRestUrl));
        } catch (MalformedURLException exception) {
            throw new ArachniClientException(exception.getMessage());
        }
    }

    /**
     * Here you can set username and password if the Arachni REST API is secured with Basic Authentication.
     * 
     * @param username Username
     * @param password Password
     * @return This builder instance.
     */
    public final ArachniRestClientBuilder addCredentials(String username, String password) {
        this.credentials = new UsernamePasswordCredentials(username, password);
        return this;
    }
    
    /**
     * Specifies the merge strategy when a conflict occurs during a merge process of scan requests.
     * 
     * @param mergeConflictStrategy The strategy to set.
     * @return This builder instance.
     */
    public final ArachniRestClientBuilder setMergeConflictStratey(MergeConflictStrategy mergeConflictStrategy) {
        this.mergeConflictStrategy = mergeConflictStrategy;
        return this;
    }

    /**
     * Returns a instance of {@code ArachniRestClient}.
     * 
     * @return Instance of {@code ArachniRestClient}.
     */
    public final ArachniClient build() {
        return new ArachniRestClient(arachniRestUrl, credentials, mergeConflictStrategy);
    }
}
