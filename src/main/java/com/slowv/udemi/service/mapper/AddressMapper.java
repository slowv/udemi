package com.slowv.udemi.service.mapper;

import com.slowv.udemi.entity.AddressEntity;
import com.slowv.udemi.service.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper extends EntityMapper<AddressDto, AddressEntity> {
}
