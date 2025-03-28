package com.slowv.udemi.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignRegisterCourseRequest {
    @NotNull
    Long studentId;
    @NotNull
    Long teacherId;
}
