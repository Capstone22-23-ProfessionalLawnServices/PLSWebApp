package com.professionallawnservices.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class PlsExceptionHandler {

    @ExceptionHandler(value = {PlsRequestException.class})
    public ResponseEntity<Object> handlePlsRequestException(PlsRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        PlsException plsException = new PlsException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(plsException, badRequest);
    }

    @ExceptionHandler(value = {PlsServiceException.class})
    public ResponseEntity<Object> handlePlsServiceException(PlsServiceException e) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        PlsException plsException = new PlsException(
                e.getMessage(),
                internalServerError,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(plsException, internalServerError);
    }

}
