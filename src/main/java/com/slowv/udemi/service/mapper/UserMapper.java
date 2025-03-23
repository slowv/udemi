package com.slowv.udemi.service.mapper;

import com.slowv.udemi.entity.UserEntity;
import com.slowv.udemi.service.dto.UserDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.util.ObjectUtils;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressMapper.class},
        imports = {ObjectUtils.class}
)
public interface UserMapper extends EntityMapper<UserDto, UserEntity> {
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "mapLastName")
    @Mapping(target = "fullName", expression = "java(\"%s %s\".formatted(entity.getFirstName(), entity.getLastName()))")
    @Mapping(target = "email", expression = "java(ObjectUtils.isEmpty(entity.getEmail()) ? \"slow@gmail.com\" : entity.getEmail())")
//    @Mapping(target = "id", ignore = true)
    UserDto toDto(UserEntity entity);

    @Named("mapLastName")
    default String mapLastName(String lastName) {
        return "Mr " + lastName;
    }

    @AfterMapping
    default void mappingAfterFullName(@MappingTarget UserDto dto, UserEntity entity) {
        dto.setFullName(entity.getFirstName() + " " + entity.getLastName());
    }

    @BeforeMapping
    default void mappingBeforeFullName(@MappingTarget UserDto dto, UserEntity entity) {
        dto.setFullName(entity.getFirstName() + " " + entity.getLastName());
    }
}
