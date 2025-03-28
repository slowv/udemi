package com.slowv.udemi.service.dto;

import com.slowv.udemi.entity.LessonEntity;

import java.io.Serializable;

/**
 * DTO for {@link LessonEntity}
 */
public record LessonRecord(Long id, String title) implements Serializable {
}