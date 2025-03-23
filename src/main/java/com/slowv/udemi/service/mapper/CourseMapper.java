package com.slowv.udemi.service.mapper;

import com.slowv.udemi.entity.CourseEntity;
import com.slowv.udemi.service.dto.CourseDto;
import com.slowv.udemi.service.mapper.custom.GetCategory;
import com.slowv.udemi.service.mapper.custom.handler.CustomMapperService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = DefaultMapperConfig.class,
        uses = {CustomMapperService.class}
)
public interface CourseMapper extends EntityMapper<CourseDto, CourseEntity> {

    @Override
    @Mapping(target = "categories", source = "categoryIds", qualifiedBy = {GetCategory.class})
    CourseEntity toEntity(CourseDto dto);
}
