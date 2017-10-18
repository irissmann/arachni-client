package de.irissmann.arachni.client.rest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import de.irissmann.arachni.api.ArachniApi;
import de.irissmann.arachni.api.ArachniApiException;
import de.irissmann.arachni.api.scans.Scan;

public class ArachniApiRestTest extends AbstractRestTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void tetsGetScansReturnList() throws Exception {
        stubFor(get(urlEqualTo("/scans")).willReturn(aResponse().withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(getJsonFromFile("scans.json"))));

        ArachniApi api = ArachniApiRestBuilder.create(new URL("http://127.0.0.1:8089")).build();

        List<String> scans = api.getScans();
        assertEquals(3, scans.size());
        assertTrue("List should contains element 'b4854e94746112b059c710c502949ce2'",
                scans.contains("b4854e94746112b059c710c502949ce2"));
    }
    
    @Test
    public void testReturnValueWhenPerformNewScan() throws Exception {
        stubFor(put(urlEqualTo("/scans")).willReturn(aResponse().withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(getJsonFromFile("performScan.json"))));
        
        ArachniApi api = ArachniApiRestBuilder.create(new URL("http://127.0.0.1:8089")).build();
        
        Scan scan = new Scan("http://ellen:8080");
        String id = api.performScan(scan);
        assertEquals("919813cdb162af0c091c34fca3823b89", id);
    }
    
    @Test
    public void serverNotAvailableException() throws Exception {
        ArachniApi api = ArachniApiRestBuilder.create(new URL("http://127.0.0.1:8873")).build();
        try {
            api.getScans();
        } catch(ArachniApiException exception) {
            assertEquals("Could not connect to server.", exception.getMessage());
        }
    }
}
