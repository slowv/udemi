package com.slowv.udemi.service.mapper;

import com.slowv.udemi.entity.FileHistoryDto;
import com.slowv.udemi.entity.FileHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface FileHistoryMapper extends EntityMapper<FileHistoryDto, FileHistoryEntity> {
}