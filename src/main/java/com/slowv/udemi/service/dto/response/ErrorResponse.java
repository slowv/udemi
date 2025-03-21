package com.slowv.udemi.service.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorResponse<T> {
    StatusResponse status;
    T error;

    public ErrorResponse<T> setStatus(final StatusResponse status) {
        this.status = status;
        return this;
    }

    public ErrorResponse<T> setError(final T error) {
        this.error = error;
        return this;
    }

    public static <T> ErrorResponse<T> badRequest(final T error) {
        return new ErrorResponse<T>()
                .setStatus(StatusResponse.build(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .setError(error);
    }

    public static <T> ErrorResponse<T> internalServer(final T error) {
        return new ErrorResponse<T>()
                .setStatus(StatusResponse.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()))
                .setError(error);
    }
}
