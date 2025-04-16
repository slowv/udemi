package com.slowv.udemi.web.rest.errors;

import lombok.Getter;

@Getter
public class RestTemplateException extends RuntimeException {

    private final String detail;

    public RestTemplateException(String detail) {
        super(detail);
        this.detail = detail;
    }
}
