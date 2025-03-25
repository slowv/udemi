package com.slowv.udemi.service.mapper;


import com.slowv.udemi.entity.RegisterCourseEntity;
import com.slowv.udemi.service.dto.RegisterCourseRecord;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = DefaultConfigMapper.class, uses = {AccountMapper.class, LessonProcessMapper.class})
public interface RegisterCourseMapper extends EntityMapper<RegisterCourseRecord, RegisterCourseEntity> {

    @Override
    @Mapping(target = "lessonProcesses", ignore = true)
    @Mapping(target = "student", ignore = true)
    RegisterCourseEntity toEntity(RegisterCourseRecord dto);

    @AfterMapping
    default void linkLessonProcesses(@MappingTarget RegisterCourseEntity registerCourseEntity) {
        registerCourseEntity.getLessonProcesses().forEach(lessonProcess -> lessonProcess.setRegisterCourse(registerCourseEntity));
    }
}