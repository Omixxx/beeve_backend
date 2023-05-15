# Vino Backend

## Configuration

1.  First create a `.env` file under `/vino`. Follow the `.env_example` and fill in the fields with values of your choice, many of which refer to the credentials you will use for the database
2.  For the *JWT_SECRET* in the `.env` file you just created be careful to use a hexadecimal (at minumum 256 bits) key. For that matter i invite you to generate it. Here is what i use https://www.allkeysgenerator.com/
3.  Modify the `application.yml` by filling the `username` and `password` fields, remembering to use the same credentials that you set in the `.env` file
4.  Always in the `application.yml`, under the field `url` replace `localhost` with `vino-db` which is the database container name.
5.  Start the application by running `docker-compose up -d` 

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

> as now, we are not able yet to fully handle spring security exceptions 
