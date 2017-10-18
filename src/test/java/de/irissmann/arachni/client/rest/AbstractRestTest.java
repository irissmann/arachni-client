package de.irissmann.arachni.client.rest;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import com.google.gson.JsonParser;

public abstract class AbstractRestTest {
    public String getJsonFromFile(String filename) throws Exception {
        URL url = this.getClass().getResource(filename);
        File file = new File(url.toURI());
        
        return new JsonParser().parse(new FileReader(file)).toString();
    }
}
