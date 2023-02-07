package com.professionallawnservices.app.exceptions;

public class PlsRequestException extends RuntimeException{

    public PlsRequestException(String message) {
        super(message);
    }

    public PlsRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
