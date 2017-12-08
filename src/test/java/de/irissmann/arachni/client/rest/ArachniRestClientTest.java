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
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import de.irissmann.arachni.client.ArachniClient;
import de.irissmann.arachni.client.ArachniClientException;
import de.irissmann.arachni.client.Scan;
import de.irissmann.arachni.client.rest.request.HttpParameters;
import de.irissmann.arachni.client.rest.request.ScanRequest;
import de.irissmann.arachni.client.rest.request.Scope;
import de.irissmann.arachni.client.rest.response.ResponseScan;

public class ArachniRestClientTest extends AbstractRestTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

    @Test
    public void tetsGetScansReturnList() throws Exception {
        stubFor(get(urlEqualTo("/scans")).willReturn(aResponse().withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(getJsonFromFile("responseScans.json"))));

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();

        List<String> scans = arachniClient.getScans();
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

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();

        ScanRequest scanRequest = ScanRequest.create().url("http://ellen:8080").build();
        Scan scan = arachniClient.performScan(scanRequest);
        assertEquals("919813cdb162af0c091c34fca3823b89", scan.getId());
        verify(postRequestedFor(urlEqualTo("/scans"))
                .withRequestBody(equalToJson("{\"url\":\"http://ellen:8080\"}", true, true)));
    }

    @Test(expected = ArachniClientException.class)
    public void testServerErrorWhenPerformNewScan() throws Exception {
        stubFor(post(urlEqualTo("/scans"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(ContentType.APPLICATION_JSON.toString()))
                .willReturn(aResponse().withStatus(500)
                        .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                        .withBody(getJsonFromFile("responsePerformScanError.json"))));

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();

        ScanRequest scanRequest = ScanRequest.create().url("http://ellen:8080").build();
        
        // should throw an exception because status is 500
        arachniClient.performScan(scanRequest);
    }

    @Test
    public void validateRequestWithHttp() throws Exception {
        stubFor(post(urlEqualTo("/scans"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(ContentType.APPLICATION_JSON.toString()))
                .willReturn(aResponse().withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                        .withBody(getJsonFromFile("responsePerformScan.json"))));

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();

        Scope scope = Scope.create().pageLimit(5).addExcludePathPatterns(".js|.css").build();
        HttpParameters http = HttpParameters.create().requestConcurrency(33).requestQueueSize(42)
                .requestRedirectLimit(2).requestTimeout(5000).responseMaxSize(333222).build();
        ScanRequest scanRequest = ScanRequest.create().url("http://ellen:8080").scope(scope).http(http).build();
        arachniClient.performScan(scanRequest);
        verify(postRequestedFor(urlEqualTo("/scans"))
                .withRequestBody(equalToJson(getJsonFromFile("requestScanHttp.json"), true, true)));
    }

    @Test
    public void testMonitorScanRunning() throws Exception {
        stubFor(get(urlEqualTo("/scans/123456")).willReturn(aResponse().withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(getJsonFromFile("responseMonitorScanRunning.json"))));

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();

        Scan scan = new ScanRestImpl("123456", (ArachniRestClient) arachniClient);

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

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();
        Scan scan = new ScanRestImpl("123456", (ArachniRestClient) arachniClient);

        ResponseScan response = scan.monitor();

        assertFalse(response.isBusy());
        assertEquals("done", response.getStatus());
        assertEquals("a052742a96b89f8c2ee83928a8d893cd", response.getSeed());
        assertThat(response.getErrors().size(), greaterThan(10));
    }

    @Test
    public void testDeleteScan() throws Exception {
        stubFor(delete(urlEqualTo("/scans/45c8348ef439885b819ab51cb78aa334")).willReturn(aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()).withStatus(200)));

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();
        Scan scan = new ScanRestImpl("45c8348ef439885b819ab51cb78aa334", (ArachniRestClient) arachniClient);

        assertTrue(scan.shutdown());
    }

    @Test(expected = ArachniClientException.class)
    public void testDeleteScanNotFound() throws Exception {
        stubFor(delete(urlEqualTo("/scans/45c8348ef439885b819ab51cb78aa334")).willReturn(aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withStatus(HttpStatus.SC_NOT_FOUND).withBody(getTextFromFile("responseDeleteScanNotFound.txt"))));

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();
        Scan scan = new ScanRestImpl("45c8348ef439885b819ab51cb78aa334", (ArachniRestClient) arachniClient);

        // should throw an exception
        assertTrue(scan.shutdown());
    }

    @Test
    public void testGetReportJson() throws Exception {
        stubFor(get(urlEqualTo("/scans/30dd87231d9022e97fca7f34b66ece43/report.json")).willReturn(aResponse()
                .withStatus(200).withHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .withBody(getJsonFromFile("responseReport.json"))));

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();
        Scan scan = new ScanRestImpl("30dd87231d9022e97fca7f34b66ece43", (ArachniRestClient) arachniClient);

        String report = scan.getReportJson();

        JSONAssert.assertEquals("{\"version\": \"1.5.1\"}", report, false);
    }

    @Test
    public void testGetReportHtml() throws Exception {
        stubFor(get(urlEqualTo("/scans/30dd87231d9022e97fca7f34b66ece43/report.html.zip"))
                .willReturn(aResponse().withStatus(200).withHeader(HttpHeaders.CONTENT_TYPE, "application/zip")
                        .withBody(getByteArrayFromFile("responseReport.html.zip"))));

        ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();
        Scan scan = new ScanRestImpl("30dd87231d9022e97fca7f34b66ece43", (ArachniRestClient) arachniClient);

        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        scan.getReportHtml(outstream);

        assertEquals(2852, outstream.size());
    }

    @Test(expected = ArachniClientException.class)
    public void serverNotAvailableException() throws Exception {
        ArachniClient arachniClient = ArachniRestClientBuilder.create("http://127.0.0.1:8873").build();
        
        // should throw an Exception
        arachniClient.getScans();
    }
    
    @Test(expected = ArachniClientException.class)
    public void testExceptionForMalformedUrl() throws Exception {
        ArachniRestClientBuilder.create("foo").build();
    }

    private String getUrl() throws MalformedURLException {
        return "http://127.0.0.1:" + wireMockRule.port();
    }
}
