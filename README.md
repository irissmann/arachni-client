arachni-client
==============

Introduction
------------

Usage
-----
This client provides a very simple api to send REST request to an Arachni 
REST server.

Use the builder to get an instance of the <code>[ArachniClient]</code>.
```java
ArachniClient arachniClient = ArachniRestClientBuilder.create(getUrl()).build();
```