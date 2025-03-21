package com.slowv.udemi.controller.errors;

public class EmailExistException extends RuntimeException {

    public EmailExistException(final String message) {
        super(message);
    }
}
