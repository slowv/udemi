package com.slowv.udemi.controller.errors;

import com.slowv.udemi.service.dto.response.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(EmailExistException.class)
    public ErrorResponse<String> handleEmailExistException(EmailExistException e) {
        return ErrorResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse<String> handleException(Exception e) {
        return ErrorResponse.internalServer(e.getMessage());
    }
}
