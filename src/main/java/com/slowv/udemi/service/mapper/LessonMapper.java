package com.slowv.udemi.service.mapper;

import com.slowv.udemi.entity.LessonEntity;
import com.slowv.udemi.service.dto.LessonRecord;
import com.slowv.udemi.service.dto.request.AddLessonRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonMapper extends EntityMapper<LessonRecord, LessonEntity> {
    LessonEntity toEntity(AddLessonRequest request);
}