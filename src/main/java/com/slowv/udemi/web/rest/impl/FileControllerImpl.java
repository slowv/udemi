package com.slowv.udemi.web.rest.impl;

import com.slowv.udemi.entity.FileHistoryDto;
import com.slowv.udemi.integration.storage.model.UploadFileAgrs;
import com.slowv.udemi.service.FileHistoryService;
import com.slowv.udemi.service.UploadService;
import com.slowv.udemi.service.dto.request.GetHistoryFilterRequest;
import com.slowv.udemi.service.dto.request.UploadDocumentRequest;
import com.slowv.udemi.service.dto.request.UploadFileRequest;
import com.slowv.udemi.service.dto.response.PagingResponse;
import com.slowv.udemi.service.dto.response.Response;
import com.slowv.udemi.service.dto.response.UploadDocumentResponse;
import com.slowv.udemi.web.rest.FileController;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {

    private final UploadService uploadService;
    private final FileHistoryService fileHistoryService;

    @SneakyThrows
    @Override
    public Response<String> upload(final UploadFileRequest request) {
        log.info("Start upload file");
        // Tạo file tạm
        final var multipartFile = request.file();

        final var uploadFileAgrs = UploadFileAgrs.builder()
                .size(multipartFile.getSize())
                .contentType(multipartFile.getContentType())
                .filename(multipartFile.getOriginalFilename())
                .inputStream(multipartFile.getInputStream())
                .path("/video")
                .build();
        // Gửi sang service xử lý bất đồng bộ
        uploadService.upload(uploadFileAgrs);
        log.info("Done upload file");
        return Response.ok("File đang được xử lý vui lòng kiểm tra lịch sử.");
    }

    @Override
    public Response<PagingResponse<FileHistoryDto>> histories(final GetHistoryFilterRequest request) {
        return Response.ok(
                PagingResponse.from(
                        fileHistoryService.histories(request)
                )
        );
    }

    @Override
    public Response<UploadDocumentResponse> uploadDocument(final UploadDocumentRequest request) {
        return Response.ok(uploadService.upload(request));
    }
}
