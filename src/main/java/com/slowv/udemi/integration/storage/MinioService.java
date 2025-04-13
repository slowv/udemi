package com.slowv.udemi.integration.storage;

import com.slowv.udemi.common.utils.ConverterUtils;
import com.slowv.udemi.integration.storage.model.UploadFileAgrs;
import com.slowv.udemi.web.rest.errors.BusinessException;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("minioService")
@RequiredArgsConstructor
public final class MinioService implements S3Service {
    private static final String BUCKET = "resources";

    private final MinioClient minioClient;

    @PostConstruct
    private void init() {
        createBucket(BUCKET);
    }

    @SneakyThrows
    private void createBucket(final String bucket) {
        // Kiểm tra nếu bucket chưa tồn tại thì sẽ tạo mới
        final var found = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucket)
                        .build()
        );

        // Nếu đã tồn tại thì return
        if (found) {
            return;
        }

        // Tạo bucket nếu chưa tồn tại
        minioClient.makeBucket(
                MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build()
        );

        // Thiết lập bucket là public bằng cách set policy
        final var policy = """
                    {
                      "Version": "2012-10-17",
                      "Statement": [
                       {
                          "Effect": "Allow",
                          "Principal": "*",
                          "Action": "s3:GetObject",
                          "Resource": "arn:aws:s3:::%s/*"
                        }
                      ]
                    }
                """.formatted(bucket);

        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                        .bucket(bucket)
                        .config(policy)
                        .build()
        );
    }

    @SneakyThrows
    @Override
    public String upload(final UploadFileAgrs request) {
        final var filename = request.getFilename();
        log.info("Bucket: {} - file name: {} - file size: {}", BUCKET, filename, request.getSize());

        final var pathFile = String.join("/", request.getPath(), filename);
        try {
            final var response = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET)
                            .object(pathFile)
                            .contentType(Optional.ofNullable(request.getContentType()).orElse("image/png; image/jpg;"))
                            .stream(request.getInputStream(), request.getSize(), -1)
                            .build()
            );
            log.info("Response: {}", response);
        } catch (Exception e) {
            log.error("Error saving file \n {} ", e.getMessage());
            throw new BusinessException("400", "Unable to upload file", e);
        }

        // Trả về link file
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(BUCKET)
                        .object(pathFile)
                        .build()
        );
    }

    @Override
    public byte[] download(final String bucket, final String fileName) {
        try (final var response = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .build())) {
            final var contentLength = response.headers().get(HttpHeaders.CONTENT_LENGTH);
            final var size = StringUtils.isEmpty(contentLength) ? 0 : Integer.parseInt(contentLength);
            return ConverterUtils.readBytesFromInputStream(response, size);
        } catch (Exception e) {
            throw new BusinessException("400", "Unable to download file", e);
        }
    }
}
