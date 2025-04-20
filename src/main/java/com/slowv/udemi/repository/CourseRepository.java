package com.slowv.udemi.repository;

import com.slowv.udemi.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRepository extends
        JpaRepository<CourseEntity, Long>,
        JpaSpecificationExecutor<CourseEntity> {
}