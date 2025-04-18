package com.slowv.udemi.service.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobDetailDto {
    String jobName;
    String jobGroup;
    String jobDescription;
}
