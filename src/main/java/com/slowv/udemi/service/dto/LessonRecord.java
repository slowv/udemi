package com.slowv.udemi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slowv.udemi.entity.LessonEntity;

import java.io.Serializable;

/**
 * DTO for {@link LessonEntity}
 */
public record LessonRecord(
        Long id,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String title
) implements Serializable {
}