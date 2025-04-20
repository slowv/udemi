package com.slowv.udemi.service.mapper;

import com.slowv.udemi.entity.CourseEntity;
import com.slowv.udemi.service.dto.CourseRecord;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(
        config = DefaultConfigMapper.class,
        uses = {LessonMapper.class}
)
public interface CourseMapper extends EntityMapper<CourseRecord, CourseEntity> {
    @AfterMapping
    default void linkLessons(@MappingTarget CourseEntity courseEntity) {
        courseEntity.getLessons().forEach(lesson -> lesson.setCourse(courseEntity));
    }
}