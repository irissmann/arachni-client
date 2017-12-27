arachni-client
==============

Introduction
------------
Java client for the [Arachni Scanner](http://www.arachni-scanner.com/) REST API. It's developed and tested with version
1.5.1-0.5.12 of the Arachni project.

Maven
-----
The arachni-client artifacts are available on Maven central repository.
```xml
<!-- https://mvnrepository.com/artifact/de.irissmann/arachni-client -->
<dependency>
    <groupId>de.irissmann</groupId>
    <artifactId>arachni-client</artifactId>
    <version>1.0.1</version>
</dependency>
```

Usage
-----
This client provides a very simple api to send REST request to an Arachni 
REST server.

Use the builder to get an instance of the ArachniClient. Just replace the url
with the address of your Arachni REST server.
```java
ArachniClient arachniClient = ArachniRestClientBuilder
    .create("http://127.0.0.1:8080")
    .build();
```

If your Arachni REST server is secured with basic authentication, use the
following command.
```java
ArachniClient arachniClient = ArachniRestClientBuilder
    .create("http://127.0.0.1:8080")
    .addCredentials("username", "password")
    .build();
```

To perform a new scan create a ScanRequest. It's best to use the fluent API like in the folling example:
```java
ScanRequest scanRequest = ScanRequest.create().url("http://address:port").build();
Scan scan = arachniClient.performScan(scanRequest);
```

Request the status from a scan:
```java
ScanResponse response = scan.monitor();
```

Download a generated HTML report from the server:
```java
OutputStream outstream = new FileOutputStream(reportFile);
scan.getReportHtml(outstream);
```

For more information see the Java Doc and the [ARACHNI REST API](https://github.com/Arachni/arachni/wiki/REST-API).
