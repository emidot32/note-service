package com.interview.noteservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    // We can return some custom class here
    public ResponseEntity<String> handleCommonException(Exception ex) {
        log.error("The exception is found: ", ex);
        // Returns only abstract message because the exception can contain sensitive information
        // about used libs and frameworks. It is not secure.
        return new ResponseEntity<>(
                "There are some troubles. Please contact your administrator",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NoEntityFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> handleNoEntityFoundException(NoEntityFoundException ex) {
        log.error("The exception is found: ", ex);
        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }
}
