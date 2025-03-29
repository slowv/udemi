package com.slowv.udemi.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record SignUpRequest(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String firstName,
        String phone,
        @NotBlank
        String lastName,
        String introduce,
        MultipartFile avatar
) {
}
