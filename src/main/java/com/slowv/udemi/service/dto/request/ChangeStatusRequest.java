package com.slowv.udemi.service.dto.request;


import com.slowv.udemi.entity.enums.RegisterLessonStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ChangeStatusRequest(
        @NotBlank RegisterLessonStatus status,
        @NotEmpty List<Long> courseIds
) {
}
