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

## Foo API
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
