package com.slowv.udemi.entity;

import com.slowv.udemi.entity.enums.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
public class RoleEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "role_seq")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20, nullable = false)
    ERole name = ERole.STUDENT;

    public RoleEntity() {
    }

    public RoleEntity(final ERole name) {
        this.name = name;
    }
}
