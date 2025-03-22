package com.slowv.udemi.controller.errors;

import lombok.Getter;

import java.util.Map;

@Getter
public class FieldValidationException extends RuntimeException {
    private final Map<String, Object> errors;

    public FieldValidationException(final String message, final Map<String, Object> errors) {
        super(message);
        this.errors = errors;
    }
}
