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

package de.irissmann.arachni.client.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines scope parameter settings for a scan request.
 * 
 * @author Ingo Rissmann
 * @since 1.0.0
 *
 */
@SuppressWarnings("unused")
public class Scope {
    
    private Integer pageLimit;
    
    private List<String> excludePathPatterns;
    
    Scope() {
        super();
    }
    
    void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }
    
    void setExcludePathPatterns(List<String> excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }
    
    /**
     * Returns a builder to create a {@code Scope} object.
     * 
     * @return The {@code ScopeBuilder}.
     */
    public static final ScopeBuilder create() {
        return new ScopeBuilder();
    }
}
