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

package de.irissmann.arachni.client;

import java.util.List;

import de.irissmann.arachni.client.request.ScanRequest;

/**
 * Interface to send requests to an Arachni Security Scanner instance. Use a builder to get an instance. The following
 * example creates an instance of {@code ArachniRestClient}
 * 
 * <pre>
 * {@code
 * ArachniClient arachniClient = ArachniRestClientBuilder
 *   .create("http://127.0.0.1:8080")
 *   .build();
 * }
 * </pre>
 * 
 * @author Ingo Rissmann
 * @see de.irissmann.arachni.client.rest.ArachniRestClientBuilder
 * @since 1.0.0
 * 
 */
public interface ArachniClient {
	
	/**
	 * Returns a list with all scan id's.
	 * This method can throw the runtime exception {@code ArachniClientException}.
	 * 
	 * @return List with all scan id's.
	 * @throws ArachniClientException
	 */
	public List<String> getScans();
	
	/**
	 * Performs a new scan on the Arachni Rest Server and returns a {@code Scan} object. To start a new you have to
	 * create a {@code ScanRequest} first.
	 * 
	 * Use the returned {@code Scan} object for further activities.
	 * 
	 * @param scanRequest Request with all information needed for the scan.
	 * @return A {@code Scan} object.
	 * @throws ArachniClientException
	 * 
	 */
	public Scan performScan(ScanRequest scanRequest);
	
	/**
	 * Depends on implementation the client close connections to Arachni Security Scanner. Use this careful because
	 * after calling this method the {@code ArachniClient} and all created {@code Scan} objects can not be used anymore.
	 * 
	 * @throws ArachniClientException
	 */
	public void close();
}
