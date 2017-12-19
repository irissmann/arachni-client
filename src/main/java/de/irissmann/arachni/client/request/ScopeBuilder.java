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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a builder to create an instance of {@link Scope}. To get an instance of this builder call 
 * {@link Scope#create()}.
 * <div>
 * For default values see also the Arachni wiki page.
 * </div>
 * 
 * @author Ingo Rissmann
 * @since 1.0.0
 * 
 */
public class ScopeBuilder {

    private static final Logger log = LoggerFactory.getLogger(ScopeBuilder.class);

    private Integer pageLimit;

    private List<String> excludePathPatterns;

    /**
     * This option limits how many pages should be included in the scan.
     * 
     * @param pageLimit Number of pages to scan.
     * @return This builder instance.
     */
    public ScopeBuilder pageLimit(int pageLimit) {
        if (pageLimit > 0) {
            this.pageLimit = pageLimit;
        } else {
            log.info("PageLimit is not valid and will not be set.");
        }
        return this;
    }

    /**
     * Excludes resources whose URL matches the pattern.
     * 
     * @param pattern Pattern to exclude URL from scan.
     * @return This builder instance.
     */
    public ScopeBuilder addExcludePathPatterns(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            log.info("ExcludePathPattern is empty and will not be added.");
            return this;
        }
        if (excludePathPatterns == null) {
            excludePathPatterns = new ArrayList<String>();
        }
        excludePathPatterns.add(pattern);
        return this;
    }

    /**
     * Returns a instance of {@code Scope} to change the default settings.
     * 
     * @return A {@code Scope} object.
     */
    public Scope build() {
        Scope scope = new Scope();

        if (pageLimit != null) {
            scope.setPageLimit(pageLimit);
        }

        if (excludePathPatterns != null) {
            scope.setExcludePathPatterns(excludePathPatterns);
        }

        return scope;
    }
}
