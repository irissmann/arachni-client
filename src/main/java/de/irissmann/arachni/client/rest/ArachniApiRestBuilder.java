package de.irissmann.arachni.client.rest;

import java.net.URL;

import org.apache.http.auth.UsernamePasswordCredentials;

import de.irissmann.arachni.client.ArachniClient;

public class ArachniApiRestBuilder {

    private final URL arachniRestUrl;
    private UsernamePasswordCredentials credentials;
    

    protected ArachniApiRestBuilder(URL arachniRestUrl) {
        this.arachniRestUrl = arachniRestUrl;
    }

    public static ArachniApiRestBuilder create(URL arachniRestUrl) {
        return new ArachniApiRestBuilder(arachniRestUrl);
    }

    public final ArachniApiRestBuilder addCredentials(String username, String password) {
        this.credentials = new UsernamePasswordCredentials(username, password);
        return this;
    }
    
    public final ArachniClient build() {
        ArachniApiRestImpl restApi = new ArachniApiRestImpl();
        if (credentials != null) {
            restApi.setRestClient(new ArachniRestClient(arachniRestUrl, credentials));
        } else {
            restApi.setRestClient(new ArachniRestClient(arachniRestUrl));
        }
        return restApi;
    }
}
