package com.slowv.udemi.integration.storage.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Builder
@Value
public class UploadFileAgrs {
    //    String bucket;
    String path;
    MultipartFile multipartFile;
    String filename;
    String contentType;
    InputStream inputStream;
    long size;
}
