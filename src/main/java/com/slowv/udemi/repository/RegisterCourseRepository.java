package com.slowv.udemi.repository;


import com.slowv.udemi.entity.RegisterCourseEntity;
import com.slowv.udemi.repository.specification.custom.RegisterCourseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegisterCourseRepository extends
        JpaRepository<RegisterCourseEntity, Long>,
        JpaSpecificationExecutor<RegisterCourseEntity>,
        RegisterCourseRepositoryCustom<RegisterCourseEntity> {
}