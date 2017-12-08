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

package de.irissmann.arachni.client;

import java.io.OutputStream;

import de.irissmann.arachni.client.response.ResponseScan;

/**
 * With a {@code Scan} instance you can get all informations and reports from Arachni Security Scanner. 
 * 
 * @author Ingo Rissmann
 * */
public abstract class Scan {
    
    private String id;
    
    protected Scan(String id) {
        this.id = id;
    }
    
    /**
     * Returns the id of the scan.
     * 
     * @return The id.
     */
    public String getId() {
        return id;
    }

    /**
     * Monitors the scan and returns a {@code ReportScan} object. This method can thrown a 
     * {@code ArachniClientException}.
     * 
     * @return A {@code ReportScan} with all monitoring information.
     * @throws ArachniClientException
     */
    public abstract ResponseScan monitor();

    /**
     * This call needs to take place after each scan is done in order to prevent zombie processes.
     * Once that call is made, the scan process will be killed and removed from the service, if you wish to retrieve the
     * report you will need to do so prior to performing this call.
     *
     * @return {@code true} if shutdown was successful.
     * @throws ArachniClientException
     */
    public abstract boolean shutdown();

    /**
     * Retrieve the scan report as JSON string. This method can thrown an {@link ArachniClientException} 
     * 
     * @return The scan report as JSON string.
     * @throws ArachniClientException
     */
    public abstract String getReportJson();

    /**
     * Writes the scan HTML report in a given {@code OutputStream} as a zip file. 
     * 
     * @param outstream The {@code OutputStream} object to write the report.
     * @throws ArachniClientException
     */
    public abstract void getReportHtml(OutputStream outstream);
}
