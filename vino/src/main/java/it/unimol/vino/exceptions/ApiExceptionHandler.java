package it.unimol.vino.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(value = {ApiRequestException.class})
    protected ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleApiRequestException(UsernameNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    protected ResponseEntity<Object> handleApiRequestException(UserNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ProviderNotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(ProviderNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ItemNotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(ItemNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {UserAlreadyRegistered.class})
    protected ResponseEntity<Object> handleApiRequestException(UserAlreadyRegistered e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {PasswordNotValidException.class})
    protected ResponseEntity<Object> handleApiRequestException(PasswordNotValidException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {PermissionNotFound.class})
    protected ResponseEntity<Object> handleApiRequestException(PermissionNotFound e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PermissionAlreadyAssigned.class})
    protected ResponseEntity<Object> handleApiRequestException(PermissionAlreadyAssigned e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {PermissionAlreadyExist.class})
    protected ResponseEntity<Object> handleApiRequestException(PermissionAlreadyExist e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(value = {SectorNotFoundException.class})
    protected ResponseEntity<Object> handleApiRequestException(SectorNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {GrapeTypeNotFoundException.class})
    protected ResponseEntity<Object> handleApiRequestException(GrapeTypeNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ContributionNotFoundException.class})
    protected ResponseEntity<Object> handleApiRequestException(ContributionNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handleApiRequestException(HttpMessageNotReadableException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(CategoryNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CategoryAlreadyExistingException.class})
    public ResponseEntity<Object> handleApiRequestException(CategoryAlreadyExistingException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ProcessNotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(ProcessNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {StateNotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(StateNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ProcessHasNoStatesException.class})
    public ResponseEntity<Object> handleApiRequestException(ProcessHasNoStatesException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ProcessAlreadyStarted.class})
    public ResponseEntity<Object> handleApiRequestException(ProcessAlreadyStarted e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ProcessCancelledException.class})
    public ResponseEntity<Object> handleApiRequestException(ProcessCancelledException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {StateAlreadyExist.class})
    public ResponseEntity<Object> handleApiRequestException(StateAlreadyExist e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }
}
