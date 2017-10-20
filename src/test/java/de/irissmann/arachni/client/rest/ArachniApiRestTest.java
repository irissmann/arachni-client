package de.irissmann.arachni.client.rest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import de.irissmann.arachni.api.ArachniApi;
import de.irissmann.arachni.api.ArachniApiException;
import de.irissmann.arachni.api.scans.Http;
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
        stubFor(post(urlEqualTo("/scans"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(ContentType.APPLICATION_JSON.toString()))
                .willReturn(aResponse().withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                        .withBody(getJsonFromFile("performScan.json"))));

        ArachniApi api = ArachniApiRestBuilder.create(new URL("http://127.0.0.1:8089")).build();

        Scan scan = new Scan("http://ellen:8080");
        Http http = new Http();
        scan.setHttp(http);
        String id = api.performScan(scan);
        assertEquals("919813cdb162af0c091c34fca3823b89", id);
    }
    
    @Test
    public void testServerErrorWhenPerformNewScan() throws Exception {
        stubFor(post(urlEqualTo("/scans"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(ContentType.APPLICATION_JSON.toString()))
                .willReturn(aResponse().withStatus(500)
                        .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                        .withBody(getJsonFromFile("performScanError.json"))));

        ArachniApi api = ArachniApiRestBuilder.create(new URL("http://127.0.0.1:8089")).build();

        Scan scan = new Scan("http://ellen:8080");
        try {
            api.performScan(scan);
            fail();
        } catch (ArachniApiException exception) {
            assertThat(exception.getMessage(), containsString("RemoteException"));
        }
    }

    @Test
    public void serverNotAvailableException() throws Exception {
        ArachniApi api = ArachniApiRestBuilder.create(new URL("http://127.0.0.1:8873")).build();
        try {
            api.getScans();
            fail();
        } catch (ArachniApiException exception) {
            assertEquals("Could not connect to server.", exception.getMessage());
        }
    }
}
