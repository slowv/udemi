package com.slowv.udemi.service.dto.request;

import com.slowv.udemi.validation.FileSize;
import org.springframework.web.multipart.MultipartFile;

public record UploadFileRequest(
        @FileSize(min = 2048, max = 4096) MultipartFile file
) {
}
