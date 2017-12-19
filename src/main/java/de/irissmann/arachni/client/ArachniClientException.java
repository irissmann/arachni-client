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

/**
 * This runtime exception will be thrown if something unforeseen is happen. 
 * 
 * @author Ingo Rissmann
 * @since 1.0.0
 * 
 */
public class ArachniClientException extends RuntimeException {

    private static final long serialVersionUID = 2088423549757451992L;

    /**
     * Constructor for a new {@code ArachniClientException}.
     * 
     * @param message Message to describe the reason of this exception.
     */
    public ArachniClientException(String message) {
        super(message);
    }
    
    /**
     * Constructor for a new {@code ArachniClientException}.
     * 
     * @param message Message to describe the reason of this exception.
     * @param exception The cause exception.
     */
    public ArachniClientException(String message, Exception exception) {
        super(message, exception);
    }
}
