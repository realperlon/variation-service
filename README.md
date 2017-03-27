[![Build Status](https://travis-ci.org/realperlon/variation-service.svg?branch=master)](https://travis-ci.org/realperlon/variation-service) 

# variation-service
The RESTful backend service for the variation coding test

# Comments

1) This application is using Java best-practises
2) Test-driven-development: Unit-tests during implementation of the various steps, to verify that the code is doing what it is supposed to do.
3) Scalability: This example is loading the variation data in memory. Java-interfaces have been used to isolate the in-memory implementation. In the future, support for many more variations could be added, by providing alternate implementations of the interfaces e.g. that would stream variation data from some other data-source.
4) Performance: For fast performance this uses java parallel streams wherever possible.

# Possible next steps
* Some genes have a lot of variation data. (e.g. BRCA2 has more than 12k). Offer an extension that allows to "slice" through the results

