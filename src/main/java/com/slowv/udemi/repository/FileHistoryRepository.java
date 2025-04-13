package com.slowv.udemi.repository;

import com.slowv.udemi.entity.FileHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FileHistoryRepository extends JpaRepository<FileHistoryEntity, Long>, JpaSpecificationExecutor<FileHistoryEntity> {
    Optional<FileHistoryEntity> findByUuid(String uuid);
}