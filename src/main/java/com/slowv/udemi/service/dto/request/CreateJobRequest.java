package com.slowv.udemi.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateJobRequest {
    @NotBlank
    String jobId;
    @NotBlank
    String jobGroup;
    @NotBlank
    String jobDescription;

    List<String> emails = new ArrayList<>();
}
