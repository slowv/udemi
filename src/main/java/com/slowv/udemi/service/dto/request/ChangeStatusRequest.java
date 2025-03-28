package com.slowv.udemi.service.dto.request;


import com.slowv.udemi.entity.enums.RegisterLessonStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ChangeStatusRequest(
        @NotNull RegisterLessonStatus status,
        @NotEmpty List<Long> courseIds
) {
}
