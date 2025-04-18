package com.slowv.udemi.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartJobRequest {
    @NotBlank
    String jobId;
    @NotBlank
    String jobGroup;
    @NotBlank
    String triggerName;
    @NotBlank
    String triggerGroup;

    int intervalInSeconds;
}
