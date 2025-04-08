package com.slowv.udemi.web.rest.errors;

public class EmailExistException extends RuntimeException {

    public EmailExistException(final String message) {
        super(message);
    }
}
