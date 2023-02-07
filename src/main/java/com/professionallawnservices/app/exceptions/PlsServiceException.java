package com.professionallawnservices.app.exceptions;

public class PlsServiceException extends RuntimeException{

    public PlsServiceException(String message) {
        super(message);
    }

    public PlsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
