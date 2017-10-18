package de.irissmann.arachni.api;

import java.util.List;

public interface ArachniApi {
	
	public List<String> getScans() throws ArachniApiException;

}
