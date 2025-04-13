package com.slowv.udemi.web.rest;

import com.slowv.udemi.entity.FileHistoryDto;
import com.slowv.udemi.service.dto.request.GetHistoryFilterRequest;
import com.slowv.udemi.service.dto.request.UploadDocumentRequest;
import com.slowv.udemi.service.dto.request.UploadFileRequest;
import com.slowv.udemi.service.dto.response.PagingResponse;
import com.slowv.udemi.service.dto.response.Response;
import com.slowv.udemi.service.dto.response.UploadDocumentResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/files")
public interface FileController {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<String> upload(@Valid @ModelAttribute UploadFileRequest request);

    @PostMapping("/histories")
    Response<PagingResponse<FileHistoryDto>> histories(@RequestBody GetHistoryFilterRequest request);

    @PostMapping(value = "/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<UploadDocumentResponse> uploadDocument(@ModelAttribute UploadDocumentRequest request);
}

