package com.slowv.udemi.service;

import com.slowv.udemi.entity.FileHistoryDto;
import com.slowv.udemi.service.dto.request.GetHistoryFilterRequest;
import org.springframework.data.domain.Page;

public interface FileHistoryService {
    void initHistory(String uuid);

    void completed(final String uuid, final String uploadURL);

    void failure(String message, final String uuid);

    Page<FileHistoryDto> histories(GetHistoryFilterRequest request);
}
