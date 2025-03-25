package com.slowv.udemi.service.mapper;


import com.slowv.udemi.entity.AccountInfoEntity;
import com.slowv.udemi.service.dto.AccountInfoRecord;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface AccountInfoMapper extends EntityMapper<AccountInfoRecord, AccountInfoEntity> {
}