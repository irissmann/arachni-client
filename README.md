arachni-client
==============

Introduction
------------

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
