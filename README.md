# Vino Backend

## Configuration

1.  First create a `.env` file under `/vino`
2.  Modify the `appplication.yml` file to match your database configuration. (username and password)
3.  Start an instance of mariadb by running `docker-compose up -d` in the root directory of the project (make sure the `3306` port is avaiable, otherwise edit the docker-compose and change it).
4.  Install the dependencies by running `mvn clean install` .
5.  Run the application using `mvn spring-boot:run`

The project will be running on `localhost:8080` unless you change the port in the `application.properties` file.

## API

api should follow the following format:

`/api/v*/{resource}`

where `v*` is the version of the api. <br>Currently the version is `v1`

> #### swagger
>
> you can visit, once the application is running, the swagger documentation at `localhost:8080/swagger-ui.html`

## Exceptions

Exceptions are handled by the `ApiExceptionHandler` class. <br>We do that to give a custom format to all error responses <br> as well as eliminating all the clunky try-catch blocks we may be tempted to add.

To add a new exception first create a new class in the `exception package` and then handle it in
the `ApiExceptionHandler` class sticking to the following
format

```java
@ExceptionHandler(value = {UserNotFoundException.class})
public ResponseEntity<Object> handleApiRequestException(UserNotFoundException e){
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
}
```

as you can see, with the `@ExceptionHandler` annotation we specify the exception we want to handle and the HttpStatus we
want to return.

> as now, we are not able yet to custom handle spring security exceptions
