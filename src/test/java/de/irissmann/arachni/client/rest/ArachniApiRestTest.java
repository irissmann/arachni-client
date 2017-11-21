package de.irissmann.arachni.client.rest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import de.irissmann.arachni.client.ArachniClientException;
import de.irissmann.arachni.client.Scan;
import de.irissmann.arachni.client.ArachniClient;
import de.irissmann.arachni.client.rest.request.RequestHttp;
import de.irissmann.arachni.client.rest.request.RequestScan;
import de.irissmann.arachni.client.rest.response.ResponseScan;

public class ArachniApiRestTest extends AbstractRestTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void tetsGetScansReturnList() throws Exception {
        stubFor(get(urlEqualTo("/scans")).willReturn(aResponse().withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(getJsonFromFile("responseScans.json"))));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();

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
                        .withBody(getJsonFromFile("responsePerformScan.json"))));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();

        RequestScan scanRequest = new RequestScan("http://ellen:8080");
        RequestHttp http = new RequestHttp();
        scanRequest.setHttp(http);
        Scan scan = api.performScan(scanRequest);
        assertEquals("919813cdb162af0c091c34fca3823b89", scan.getId());
        verify(postRequestedFor(urlEqualTo("/scans")).withRequestBody(equalToJson("{\"url\":\"http://ellen:8080\"}", true, true)));
    }
    
    @Test
    public void testServerErrorWhenPerformNewScan() throws Exception {
        stubFor(post(urlEqualTo("/scans"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(ContentType.APPLICATION_JSON.toString()))
                .willReturn(aResponse().withStatus(500)
                        .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                        .withBody(getJsonFromFile("responsePerformScanError.json"))));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();

        RequestScan scanRequest = new RequestScan("http://ellen:8080");
        try {
            api.performScan(scanRequest);
            fail();
        } catch (ArachniClientException exception) {
            assertThat(exception.getMessage(), containsString("RemoteException"));
        }
    }
    
    @Test
    public void validateRequestWithHttp() throws Exception {
        stubFor(post(urlEqualTo("/scans"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(ContentType.APPLICATION_JSON.toString()))
                .willReturn(aResponse().withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                        .withBody(getJsonFromFile("responsePerformScan.json"))));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();

        RequestScan scanRequest = new RequestScan("http://ellen:8080");
        RequestHttp http = new RequestHttp().setRequestConcurrency(33)
                .setRequestQueueSize(42)
                .setRequestRedirectLimit(2)
                .setRequestTimeout(5000)
                .setResponseMaxSize(333222);
        scanRequest.setHttp(http);
        api.performScan(scanRequest);
        verify(postRequestedFor(urlEqualTo("/scans")).withRequestBody(equalToJson(getJsonFromFile("requestScanHttp.json"), true, true)));
    }
    
    @Test
    public void testMonitorScanRunning() throws Exception {
        stubFor(get(urlEqualTo("/scans/123456")).willReturn(aResponse().withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(getJsonFromFile("responseMonitorScanRunning.json"))));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();
        
        Scan scan = new ScanRestImpl("123456", (ArachniRestClient) api);
        
        ResponseScan response = scan.monitor();
        
        assertTrue(response.isBusy());
        assertEquals("scanning", response.getStatus());
        assertEquals("c0c039750bef4f5688da4fba929b06ac", response.getSeed());
        assertEquals(10, response.getStatistics().getFoundPages());
        assertEquals(2, response.getStatistics().getAuditedPages());
    }

    @Test
    public void testMonitorScanDoneWithErrors() throws Exception {
        stubFor(get(urlEqualTo("/scans/123456")).willReturn(aResponse().withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(getJsonFromFile("responseMonitorScanDoneError.json"))));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();
        Scan scan = new ScanRestImpl("123456", (ArachniRestClient) api);

        ResponseScan response = scan.monitor();
        
        assertFalse(response.isBusy());
        assertEquals("done", response.getStatus());
        assertEquals("a052742a96b89f8c2ee83928a8d893cd", response.getSeed());
        assertThat(response.getErrors().size(), greaterThan(10));
    }

    @Test
    public void testDeleteScan() throws Exception {
        stubFor(delete(urlEqualTo("/scans/45c8348ef439885b819ab51cb78aa334")).willReturn(aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withStatus(200)));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();
        Scan scan = new ScanRestImpl("45c8348ef439885b819ab51cb78aa334", (ArachniRestClient) api);

        assertTrue(scan.shutdown());
    }
    
    @Test
    public void testDeleteScanNotFound() throws Exception {
        stubFor(delete(urlEqualTo("/scans/45c8348ef439885b819ab51cb78aa334")).willReturn(aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withStatus(HttpStatus.SC_NOT_FOUND)
                .withBody(getTextFromFile("responseDeleteScanNotFound.txt"))));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();
        Scan scan = new ScanRestImpl("45c8348ef439885b819ab51cb78aa334", (ArachniRestClient) api);
        
        try {
            assertTrue(scan.shutdown());
            fail();
        } catch (ArachniClientException exception) {
            assertEquals("Scan not found for token: 45c8348ef439885b819ab51cb78aa334.", exception.getMessage());
        }
    }

    @Test
    public void testGetReportJson() throws Exception {
        stubFor(get(urlEqualTo("/scans/30dd87231d9022e97fca7f34b66ece43/report.json")).willReturn(aResponse().withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(getJsonFromFile("responseReport.json"))));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();
        Scan scan = new ScanRestImpl("30dd87231d9022e97fca7f34b66ece43", (ArachniRestClient) api);
        
        String report = scan.getReportJson();
        
        JSONAssert.assertEquals("{\"version\": \"1.5.1\"}", report, false);
    }

    @Test
    public void testGetReportHtml() throws Exception {
        stubFor(get(urlEqualTo("/scans/30dd87231d9022e97fca7f34b66ece43/report.html.zip")).willReturn(aResponse().withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, "application/zip")
                .withBody(getByteArrayFromFile("responseReport.html.zip"))));

        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8089")).build();
        Scan scan = new ScanRestImpl("30dd87231d9022e97fca7f34b66ece43", (ArachniRestClient) api);
        
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        scan.getReportHtml(outstream);
        
        assertEquals(2852, outstream.size());
    }

    @Test
    public void serverNotAvailableException() throws Exception {
        ArachniClient api = ArachniRestClientBuilder.create(new URL("http://127.0.0.1:8873")).build();
        try {
            api.getScans();
            fail();
        } catch (ArachniClientException exception) {
            assertEquals("Could not connect to server.", exception.getMessage());
        }
    }
}
