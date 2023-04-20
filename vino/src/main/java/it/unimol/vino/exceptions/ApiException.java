package it.unimol.vino.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApiException {
    private final String message;
    private final String trace;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

}
