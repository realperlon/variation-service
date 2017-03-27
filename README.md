[![Build Status](https://travis-ci.org/realperlon/variation-service.svg?branch=master)](https://travis-ci.org/realperlon/variation-service) 

# variation-service
The RESTful backend service for the variation coding test. The REST endpoint comes with a Swagger based documentation. 

# Comments

1) This application is using Java best-practises
2) Test-driven-development: Unit-tests during implementation of the various steps, to verify that the code is doing what it is supposed to do.
3) Scalability: This example is loading the variation data in memory. Java-interfaces have been used to isolate the in-memory implementation. In the future, support for many more variations could be added, by providing alternate implementations of the interfaces e.g. that would stream variation data from some other data-source.
4) Performance: For fast performance this uses java parallel streams wherever possible.

## Features not (yet) documented in the Swapper API documentation
Each REST api is available as either a JSON or XML response. The default is JSON. To enable XML append ?format=xml to a request. e.g.

```http://localhost:8080/variation-service/rest/variation/suggest/AK?format=xml```

## Possible next steps
* Some genes have a lot of variation data. (e.g. BRCA2 has more than 12k). Offer an extension that allows to "slice" through the results
* In a similar way the autosuggest-feature current responds with all matching gene names. They are never shown by the frontend, as such they could be limited to just a few suggestions. 

## Technology stack
* The REST api has been build using Jersey
* API documentation is provided using Swagger
* Java classes are mapped to XML (and json) representations using JAXB (Java Architecture for XML Binding)



