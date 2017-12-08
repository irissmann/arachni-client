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

import java.io.OutputStream;

import de.irissmann.arachni.client.Scan;
import de.irissmann.arachni.client.response.ResponseScan;

/**
 * This is a REST API implementation of the {@code Scan} interface. It is using an {@code ArachniRestClient} to 
 * communicate with a Arachni REST server.
 * 
 * @author Ingo Rissmann
 */
public class ScanRestImpl extends Scan {
    
    private ArachniRestClient restClient;
    
    ScanRestImpl(String id, ArachniRestClient restClient) {
        super(id);
        this.restClient = restClient;
    }

    /* (non-Javadoc)
     * @see de.irissmann.arachni.client.Scan#monitor()
     */
    @Override
    public ResponseScan monitor() {
        return restClient.monitor(getId());
    }

    /* (non-Javadoc)
     * @see de.irissmann.arachni.client.Scan#shutdown()
     */
    @Override
    public boolean shutdown() {
        return restClient.shutdownScan(getId());
    }

    /* (non-Javadoc)
     * @see de.irissmann.arachni.client.Scan#getReportJson()
     */
    @Override
    public String getReportJson() {
        return restClient.getScanReportJson(getId());
    }

    /* (non-Javadoc)
     * @see de.irissmann.arachni.client.Scan#getReportHtml(java.io.OutputStream)
     */
    @Override
    public void getReportHtml(OutputStream outstream) {
        restClient.getScanReportHtml(getId(), outstream);
    }

}
