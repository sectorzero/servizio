# JavaApiServerDemo
Stable framework bundling for serving RESTful APIs

## Build
```
gradle build
```

## Run
```
gradle run
```

## Hola API
```
http://localhost:8080/hola
http://localhost:8080/hola?name=Foo
```

## Foo API ( Demos use of Guice for Dependency Injecton )
```
http://localhost:8080/foo
```

## Admin API ( defaults provided by Dropwizard )
```
http://localhost:8081/
```

## API Explorer ( using Swagger )
```
http://localhost:8080/swagger
http://localhost:8080/api-docs
http://localhost:8080/api-docs/<path>
```

## Complex Data RPC Automatic Marshalling/Unmarshalling
```
http://localhost:8080/complex/single
http://localhost:8080/complex/collection
```
## Logging
* Application logging is to file at hourly rotation written to var/output/logs ( directories created automatically )
* Test logging is to console

## API which demos using a database for reading/writing a resource
```
http://localhost:8080/tokens
http://localhost:8080/tokens/{tokenId}
```
* Setup
** MariaDB installed on localhost and configuration updated appropriately in configuration/sample.yml
```
  create database sampleservice_test;
  use sampleservice_test;
  create table foodata (id int primary key, name varchar(100));
```
* Healthcheck on server start @ http://localhost:8081

