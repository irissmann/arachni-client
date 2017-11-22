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

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonParser;

public abstract class AbstractRestTest {
    
    public String getJsonFromFile(String filename) throws Exception {
        URL url = this.getClass().getResource(filename);
        File file = new File(url.toURI());
        
        return new JsonParser().parse(new FileReader(file)).toString();
    }
    
    public String getTextFromFile(String filename) throws Exception {
        URL url = this.getClass().getResource(filename);
        File file = new File(url.toURI());
        
        return Files.toString(file, Charsets.UTF_8);
    }
    
    public byte[] getByteArrayFromFile(String filename) throws Exception {
        URL url = this.getClass().getResource(filename);
        File file = new File(url.toURI());
        
        return Files.toByteArray(file);
    }
}
