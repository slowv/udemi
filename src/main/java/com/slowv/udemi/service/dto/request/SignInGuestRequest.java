package com.slowv.udemi.service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInGuestRequest(String email, @NotBlank(message = "phone must not be null!") String phone, String facebook) {
}
