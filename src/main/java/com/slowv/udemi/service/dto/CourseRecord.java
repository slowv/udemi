package com.slowv.udemi.service.dto;

import com.slowv.udemi.entity.enums.TechSkill;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.slowv.udemi.entity.CourseEntity}
 */
public record CourseRecord(
        Long id,
        @NotBlank
        String name,
        @Size(min = 1)
        List<TechSkill> skills,
        List<LessonRecord> lessons
) implements Serializable {
}