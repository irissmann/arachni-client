package de.irissmann.arachni.client;

import java.io.OutputStream;
import java.util.List;

import de.irissmann.arachni.client.rest.request.RequestScan;
import de.irissmann.arachni.client.rest.response.ResponseScan;

public interface ArachniClient {
	
	public List<String> getScans() throws ArachniClientException;
	
	public Scan performScan(RequestScan scan) throws ArachniClientException;
	
//	public ResponseScan monitorScan(String id) throws ArachniClientException;
//	
//	public boolean shutdownScan(String id) throws ArachniClientException;
//	
//	public String getScanReportJson(String id) throws ArachniClientException;
//	
//	public void getScanReportHtml(String id, OutputStream outstream) throws ArachniClientException;
	
	public void close();
}
