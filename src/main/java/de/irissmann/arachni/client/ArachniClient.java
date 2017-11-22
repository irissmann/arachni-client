package de.irissmann.arachni.client;

import java.util.List;

import de.irissmann.arachni.client.rest.request.RequestScan;

public interface ArachniClient {
	
	public List<String> getScans() throws ArachniClientException;
	
	public Scan performScan(RequestScan scan) throws ArachniClientException;
	
	public void close();
}
