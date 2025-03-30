package com.slowv.udemi.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenRecord(
        String token,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime expiry,
        String type
) {
}
