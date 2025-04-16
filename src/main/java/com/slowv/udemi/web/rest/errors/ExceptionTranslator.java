package com.slowv.udemi.web.rest.errors;

import com.slowv.udemi.service.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    //    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest()
                .body(ErrorResponse.badRequest(errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        return ErrorResponse.badRequest(
                ex.getConstraintViolations()
                        .stream()
                        .map(constraintViolation -> Pair.of(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()))
                        .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ErrorResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(RestTemplateException.class)
    public ResponseEntity<ErrorResponse<?>> handleRestTemplateException(RestTemplateException e) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.internalServer(e.getDetail()));
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<ErrorResponse<?>> handleSocketTimeoutException(SocketTimeoutException e) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.internalServer("Call Third party service timeout, please try again later!!!"));
    }

//    @ExceptionHandler(Exception.class)
//    public ErrorResponse<String> handleException(Exception e) {
//        return ErrorResponse.internalServer(e.getMessage());
//    }
}
