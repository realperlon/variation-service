[![Build Status](https://travis-ci.org/realperlon/variation-service.svg?branch=master)](https://travis-ci.org/realperlon/variation-service) 

# variation-service
The RESTful backend of the variation example

# Comments

1) This application is using Java best-practises
2) Test-driven-development: I wrote unit-tests along the way of implementing the various steps, to verify that the code is doing what it is supposed to do
3) Scalability: This example is loading the variation data in memory. Java-interfaces have been used to isolate the in-memory implementation and support for many more variations could be added in the future, by providing alternate implementations of the interfaces e.g. that would stream variation data from some other data-source.
4) Performance: For fast performance this uses java parallel streams wherever possible.

