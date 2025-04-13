package com.slowv.udemi.service.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDocumentRequest {
    private MultipartFile front;
    private MultipartFile back;
    private MultipartFile selfie;
}
