package de.irissmann.arachni.api;

import java.util.List;

import de.irissmann.arachni.client.rest.request.RequestScan;

public interface ArachniApi {
	
	public List<String> getScans() throws ArachniApiException;
	
	public String performScan(RequestScan scan) throws ArachniApiException;
}
