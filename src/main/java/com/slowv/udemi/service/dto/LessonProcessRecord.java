package com.slowv.udemi.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.slowv.udemi.entity.LessonProcessEntity;
import com.slowv.udemi.entity.enums.ProcessStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link LessonProcessEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LessonProcessRecord(
        String createdBy,
        @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy, Long id, ProcessStatus status
) implements Serializable {
}