Dropwizard / Swagger Integration
--------------------------------
Content in this package copied from https://github.com/federecio/dropwizard-swagger

Purpose :
* In dropwizard-swagger, the static content is copied from Swagger-UI/dist. The exact content available in
the dropwiard-content does not really work correctly. So the setup Java classes were copied here and the
static Swagger-UI/dist content has been copied directly here from https://github.com/swagger-api/swagger-ui

Changes :
* dropwizard-swagger does not provide access to adding information to ApiInfo in SwaggerConfig. Changes
have been made here to inject this from Application Configuration
* dropwizard-swagger hostname resolution has been updated to use specified hostname if provided