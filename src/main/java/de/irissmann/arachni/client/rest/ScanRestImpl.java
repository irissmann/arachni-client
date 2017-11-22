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

import de.irissmann.arachni.client.ArachniClientException;
import de.irissmann.arachni.client.Scan;
import de.irissmann.arachni.client.rest.response.ResponseScan;

public class ScanRestImpl extends Scan {
    
    private ArachniRestClient restClient;
    
    ScanRestImpl(String id, ArachniRestClient restClient) {
        super(id);
        this.restClient = restClient;
    }

    @Override
    public ResponseScan monitor() throws ArachniClientException {
        return restClient.monitor(getId());
    }

    @Override
    public boolean shutdown() throws ArachniClientException {
        return restClient.shutdownScan(getId());
    }

    @Override
    public String getReportJson() throws ArachniClientException {
        return restClient.getScanReportJson(getId());
    }

    @Override
    public void getReportHtml(OutputStream outstream) throws ArachniClientException {
        restClient.getScanReportHtml(getId(), outstream);
    }

}
