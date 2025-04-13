package com.slowv.udemi.entity;

import com.slowv.udemi.entity.enums.FileUploadStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "file_history")
public class FileHistoryEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(columnDefinition = "TEXT")
    String url;
    
    @Column(unique = true, nullable = false)
    String uuid;

    FileUploadStatus status = FileUploadStatus.UPLOADING;

    @Column(columnDefinition = "TEXT")
    @Comment("Lý do lỗi")
    String reason;
}
