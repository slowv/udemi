package com.slowv.udemi.integration.storage;


import com.slowv.udemi.integration.storage.model.UploadFileAgrs;

public sealed interface S3Service permits MinioService {
    String upload(UploadFileAgrs request);

    byte[] download(String bucket, String fileName);
}
