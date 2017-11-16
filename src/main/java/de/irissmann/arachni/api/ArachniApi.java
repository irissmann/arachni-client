package de.irissmann.arachni.api;

import java.io.OutputStream;
import java.util.List;

import de.irissmann.arachni.client.rest.request.RequestScan;
import de.irissmann.arachni.client.rest.response.ResponseScan;

public interface ArachniApi {
	
	public List<String> getScans() throws ArachniApiException;
	
	public String performScan(RequestScan scan) throws ArachniApiException;
	
	public ResponseScan monitorScan(String id) throws ArachniApiException;
	
	public boolean shutdownScan(String id) throws ArachniApiException;
	
	public String getScanReportJson(String id) throws ArachniApiException;
	
	public void getScanReportHtml(String id, OutputStream outstream) throws ArachniApiException;
}
