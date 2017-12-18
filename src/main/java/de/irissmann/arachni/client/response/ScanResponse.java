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

package de.irissmann.arachni.client.response;

import java.util.List;

public class ScanResponse {
    
    private boolean busy;
    
    private String status;
    
    private String seed;
    
    private List<String> errors;
    
    private Statistics statistics;
    
    public boolean isBusy() {
        return busy;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getSeed() {
        return seed;
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public Statistics getStatistics() {
        return statistics;
    }
}