package com.slowv.udemi.service.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<D> toDto(List<E> entities);

    List<E> toEntity(List<D> dtos);

//    @Named(value = "update")
//    @BeanMapping(
//            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
//            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
//    void update(D dto, @MappingTarget E entity);
//
//    @BeanMapping(
//            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
//            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
//    void updateDto(@MappingTarget D dto, E entity);
}
