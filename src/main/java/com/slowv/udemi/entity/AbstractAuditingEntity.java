package com.slowv.udemi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdDate", "lastModifiedDate", "createdBy", "lastModifiedBy" }, allowGetters = true)
public abstract class AbstractAuditingEntity<ID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Comment("Được tạo bởi account nào")
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy = "system";

    @Comment("Được tạo ngày nào")
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Comment("Được cập nhật ngày nào")
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Comment("Được cập nhật bởi account nào")
    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy = "system";

    public abstract ID getId();
}
