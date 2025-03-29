package com.slowv.udemi.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenRecord(
        String token,
        LocalDateTime expiry,
        String type
) {
}
