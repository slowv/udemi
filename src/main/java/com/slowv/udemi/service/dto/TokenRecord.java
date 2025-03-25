package com.slowv.udemi.service.dto;

import java.time.LocalDateTime;

public record TokenRecord(
        String token,
        LocalDateTime expiry,
        String type
) {
}
