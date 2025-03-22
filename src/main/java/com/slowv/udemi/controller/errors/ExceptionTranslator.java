package com.slowv.udemi.controller.errors;

import com.slowv.udemi.service.dto.response.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(EmailExistException.class)
    public ErrorResponse<String> handleEmailExistException(EmailExistException e) {
        return ErrorResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(FieldValidationException.class)
    public ErrorResponse<Map<String, Object>> handleFieldValidationException(FieldValidationException e) {
        return ErrorResponse.badRequest(e.getErrors());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse<String> handleException(Exception e) {
        return ErrorResponse.internalServer(e.getMessage());
    }
}
