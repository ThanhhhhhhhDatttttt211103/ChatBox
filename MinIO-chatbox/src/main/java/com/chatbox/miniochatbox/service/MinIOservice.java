package com.chatbox.miniochatbox.service;

import com.chatbox.miniochatbox.constant.MinIOConstant;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class MinIOservice {
    private final MinioClient minioClient;

    @Autowired
    public MinIOservice(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void uploadFile(String fileName, String contentType, InputStream inputStream) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(MinIOConstant.bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(MinIOConstant.bucketName).build());
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(MinIOConstant.bucketName)
                            .object(fileName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public InputStream getObject(String pathFile) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(MinIOConstant.bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(MinIOConstant.bucketName).build());
            }

            InputStream stream = minioClient
                    .getObject(GetObjectArgs.builder().bucket(MinIOConstant.bucketName).object(pathFile).build());
            return stream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
