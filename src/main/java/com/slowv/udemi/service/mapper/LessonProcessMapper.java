package com.slowv.udemi.service.mapper;


import com.slowv.udemi.entity.LessonProcessEntity;
import com.slowv.udemi.service.dto.LessonProcessRecord;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface LessonProcessMapper extends EntityMapper<LessonProcessRecord, LessonProcessEntity> {

    @Override
    LessonProcessEntity toEntity(LessonProcessRecord dto);
}