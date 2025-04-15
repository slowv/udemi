package com.slowv.udemi.service.impl;

import com.slowv.udemi.component.MultipartFileContextHolder;
import com.slowv.udemi.integration.storage.S3Service;
import com.slowv.udemi.integration.storage.model.UploadFileAgrs;
import com.slowv.udemi.service.FileHistoryService;
import com.slowv.udemi.service.UploadService;
import com.slowv.udemi.service.dto.request.UploadDocumentRequest;
import com.slowv.udemi.service.dto.response.UploadDocumentResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final S3Service s3Service;
    private final FileHistoryService fileHistoryService;
    @Autowired
    @Qualifier("taskExecutor")
    private Executor executor;

    @Async
    @Override
    public void upload(UploadFileAgrs uploadFileAgrs) {
        final var uuid = UUID.randomUUID().toString();
        fileHistoryService.initHistory(uuid);
        try {
            log.info("Uploading file {}", uuid);
            final var uploadURL = s3Service.upload(uploadFileAgrs);

            log.info(uploadURL);
            fileHistoryService.completed(uuid, uploadURL);
        } catch (Exception ex) {
            fileHistoryService.failure(ex.getMessage(), uuid);
            ex.printStackTrace();
        } finally {
            MultipartFileContextHolder.clear();
        }
    }

    @SneakyThrows
    @Override
    public UploadDocumentResponse upload(final UploadDocumentRequest request) {
        final var uploadFront = buildCompleteFutureUpload(request.getFront());
        final var uploadBack = buildCompleteFutureUpload(request.getBack());
        final var uploadSelfie = buildCompleteFutureUpload(request.getSelfie());

        final var completableFuture = CompletableFuture.allOf(uploadFront, uploadBack, uploadSelfie);

        log.info("Start completableFuture");
        completableFuture.get();
        log.info("Done completableFuture");
        return new UploadDocumentResponse(uploadFront.get(), uploadBack.get(), uploadSelfie.get());

//        final var front = uploadFile(request.getFront());
//        final var back = uploadFile(request.getBack());
//        final var selfie = uploadFile(request.getSelfie());
//        return new UploadDocumentResponse(front, back, selfie);
    }

    private CompletableFuture<String> buildCompleteFutureUpload(final MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Uploading file {}", file.getOriginalFilename());
            try {
                Thread.sleep(2 * 1000);
                final var uploadFileAgrs = UploadFileAgrs.builder()
                        .filename(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .size(file.getSize())
                        .inputStream(file.getInputStream())
                        .build();
                return s3Service.upload(uploadFileAgrs);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }

    @SneakyThrows
    private String uploadFile(final MultipartFile file) {
        Thread.sleep(2 * 1000);
        final var uploadFileAgrs = UploadFileAgrs.builder()
                .filename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .inputStream(file.getInputStream())
                .build();
        return s3Service.upload(uploadFileAgrs);
    }
}
