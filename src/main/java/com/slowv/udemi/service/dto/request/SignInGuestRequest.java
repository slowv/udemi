package com.slowv.udemi.service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInGuestRequest(String email, @NotBlank String phone, String facebook) {
}
