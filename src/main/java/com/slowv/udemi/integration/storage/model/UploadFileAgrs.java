package com.slowv.udemi.integration.storage.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Value
public class UploadFileAgrs {
    //    String bucket;
    String path;
    MultipartFile file;
}
