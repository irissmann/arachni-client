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

/**
 * Some scan statistics. 
 * 
 * @author Ingo Rissmann
 * @since 1.0.0
 *
 */
public class Statistics {
    
    private int foundPages;
    
    private int auditedPages;
    
    /**
     * Returns the number of pages found for the scan.
     * 
     * @return Number of pages.
     */
    public int getFoundPages() {
        return foundPages;
    }
    
    /**
     * Returns the number of pages already audited.
     * 
     * @return Number audited pages.
     */
    public int getAuditedPages() {
        return auditedPages;
    }
}
