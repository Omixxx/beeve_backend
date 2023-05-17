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

    @ExceptionHandler(value = {PermissionNotFoundException.class})
    protected ResponseEntity<Object> handleApiRequestException(PermissionNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PermissionAlreadyAssignedException.class})
    protected ResponseEntity<Object> handleApiRequestException(PermissionAlreadyAssignedException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {PermissionAlreadyExistException.class})
    protected ResponseEntity<Object> handleApiRequestException(PermissionAlreadyExistException e) {
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

    @ExceptionHandler(value = {ProcessAlreadyStartedException.class})
    public ResponseEntity<Object> handleApiRequestException(ProcessAlreadyStartedException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ProcessAbortedException.class})
    public ResponseEntity<Object> handleApiRequestException(ProcessAbortedException e) {
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

    @ExceptionHandler(value = {QuantityNotAvailableException.class})
    public ResponseEntity<Object> handleApiRequestException(QuantityNotAvailableException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ProcessIsCompletedException.class})
    public ResponseEntity<Object> handleApiRequestException(ProcessIsCompletedException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {WasteNotAllowedException.class})
    public ResponseEntity<Object> handleApiRequestException(WasteNotAllowedException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DuplicateStateException.class})
    public ResponseEntity<Object> handleApiRequestException(DuplicateStateException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.CONFLICT,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ProcessDidNotProgressException.class})
    public ResponseEntity<Object> handleApiRequestException(ProcessDidNotProgressException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
