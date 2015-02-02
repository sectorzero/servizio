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

## Admin API ( defaults provided by Dropwizard )
```
http://localhost:8081/
```

## API Documentation and Explorer ( using Swagger )
```
http://localhost:8080/swagger
http://localhost:8080/api-docs
http://localhost:8080/api-docs/<path>
```

## Logging
* Application logging is to file at hourly rotation written to var/output/logs ( directories created automatically )
* Test logging is to console

## API Demos

### /hola ( Simple Hello World )
```
http://localhost:8080/hola
http://localhost:8080/hola?name=Foo
```

### /foo ( Demos use of Guice for Dependency Injecton )
```
http://localhost:8080/foo
```
## /complex ( Automatic Marshalling/Unmarshalling for Complex Data RPC )
```
http://localhost:8080/complex/single
http://localhost:8080/complex/collection
```

## /tokens ( Using a database for reading/writing a resource )
```
http://localhost:8080/tokens ( GET, POST )
http://localhost:8080/tokens/{tokenId} ( GET )
```
### Setup
- MariaDB installed on localhost
- Configuration updated appropriately in configuration/sample.yml ( username/password )
- Schema setup
```
  create database sampleservice_test;
  use sampleservice_test;
  create table foodata (id int primary key, name varchar(100));
```
- Start the app : gradle run
- Run healthcheck to see if connected to DB @ http://localhost:8081
- Execute the APIs using the Swagger UI ( http://localhost:8080/swagger ) or your fav client.

