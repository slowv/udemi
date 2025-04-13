package com.slowv.udemi.service.impl;

import com.slowv.udemi.entity.FileHistoryDto;
import com.slowv.udemi.entity.FileHistoryEntity;
import com.slowv.udemi.entity.enums.FileUploadStatus;
import com.slowv.udemi.repository.FileHistoryRepository;
import com.slowv.udemi.service.FileHistoryService;
import com.slowv.udemi.service.dto.request.GetHistoryFilterRequest;
import com.slowv.udemi.service.mapper.FileHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileHistoryServiceImpl implements FileHistoryService {

    private final FileHistoryRepository fileHistoryRepository;
    private final FileHistoryMapper fileHistoryMapper;

    @Async("taskExecutor")
    @Override
    public void initHistory(final String uuid) {
        final var fileHistory = new FileHistoryEntity();
        fileHistory.setUuid(uuid);

        fileHistoryRepository.save(fileHistory);
    }

    @Async("taskExecutor")
    @Override
    public void completed(final String uuid, final String uploadURL) {
        fileHistoryRepository.findByUuid(uuid).ifPresent(fileHistory -> {
            fileHistory.setStatus(FileUploadStatus.COMPLETED);
            fileHistory.setUrl(uploadURL);
            fileHistoryRepository.save(fileHistory);
        });
    }

    @Async("taskExecutor")
    @Override
    public void failure(final String message, final String uuid) {
        fileHistoryRepository.findByUuid(uuid).ifPresent(fileHistory -> {
            fileHistory.setStatus(FileUploadStatus.ERROR);
            fileHistory.setReason(message);
            fileHistoryRepository.save(fileHistory);
        });
    }

    @Override
    public Page<FileHistoryDto> histories(final GetHistoryFilterRequest request) {
        return fileHistoryRepository.findAll(request.specification(), request.getPaging().pageable())
                .map(fileHistoryMapper::toDto);
    }
}
