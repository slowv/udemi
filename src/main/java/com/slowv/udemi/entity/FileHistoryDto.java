package com.slowv.udemi.entity;

import com.slowv.udemi.entity.enums.FileUploadStatus;

import java.io.Serializable;

/**
 * DTO for {@link FileHistoryEntity}
 */
public record FileHistoryDto(Long id, String url, String uuid, FileUploadStatus status,
                             String reason) implements Serializable {
}