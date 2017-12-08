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

import de.irissmann.arachni.client.rest.response.ResponseScan;

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
    
    public String getId() {
        return id;
    }

    public abstract ResponseScan monitor() throws ArachniClientException;

    public abstract boolean shutdown() throws ArachniClientException;

    public abstract String getReportJson() throws ArachniClientException;

    public abstract void getReportHtml(OutputStream outstream) throws ArachniClientException;
}
