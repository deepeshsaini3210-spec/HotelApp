package com.grandstay.hotel.integration.Service;

import io.minio.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class StorageMiniOService {

    private final MinioClient minioClient;

    @Value("${minio.url:http://localhost:9200}")
    private String minioUrl;

    @Value("${minio.bucket.name:billing-bucket}")
    private String bucketName;

    public StorageMiniOService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /** Uploads file to MinIO and returns the public URL to access it. */
    public String uploadFile(MultipartFile file) throws Exception {
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) originalName = "file";
        String objectName = UUID.randomUUID() + "_" + originalName;

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType() != null ? file.getContentType() : "application/octet-stream")
                        .build()
        );

        return minioUrl + "/" + bucketName + "/" + objectName;
    }


    /** Download file from MinIO by object name. Returns input stream; caller must close it. */
    public InputStream downloadFile(String objectName) throws Exception {
        if (objectName == null || objectName.isBlank()) {
            throw new IllegalArgumentException("Object name cannot be null or blank");
        }
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

}