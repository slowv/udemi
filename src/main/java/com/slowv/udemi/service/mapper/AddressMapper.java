package com.slowv.udemi.service.mapper;

import com.slowv.udemi.entity.AddressEntity;
import com.slowv.udemi.service.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class)
public interface AddressMapper extends EntityMapper<AddressDto, AddressEntity> {
}
