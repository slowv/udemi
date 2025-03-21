package com.slowv.udemi.service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Response<T> {
    StatusResponse status;
    T data;

    private Response(StatusResponse status) {
        this.status = status;
    }

    public static Response<Void> noContent() {
        return new Response<>(StatusResponse.build(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase()));
    }

    private Response<T> setStatus(final StatusResponse status) {
        this.status = status;
        return this;
    }

    private Response<T> setData(final T data) {
        this.data = data;
        return this;
    }

    public static <T> Response<T> ok(T data) {
        return new Response<T>()
                .setStatus(StatusResponse.build(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()))
                .setData(data);
    }

    public static <T> Response<T> created(final T data) {
        return new Response<T>()
                .setStatus(StatusResponse.build(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase()))
                .setData(data);
    }
}
