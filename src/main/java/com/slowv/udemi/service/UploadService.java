package com.slowv.udemi.service;

import com.slowv.udemi.integration.storage.model.UploadFileAgrs;
import com.slowv.udemi.service.dto.request.UploadDocumentRequest;
import com.slowv.udemi.service.dto.response.UploadDocumentResponse;

public interface UploadService {
    void upload(UploadFileAgrs uploadFileAgrs);

    UploadDocumentResponse upload(UploadDocumentRequest request);
}
