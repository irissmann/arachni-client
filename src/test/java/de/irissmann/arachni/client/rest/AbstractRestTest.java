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
