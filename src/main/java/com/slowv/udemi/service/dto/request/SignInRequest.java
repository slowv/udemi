package com.slowv.udemi.service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
