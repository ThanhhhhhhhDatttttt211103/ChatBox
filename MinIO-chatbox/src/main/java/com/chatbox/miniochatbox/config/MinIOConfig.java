package com.chatbox.miniochatbox.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
public class MinIOConfig {
    @Value("${minIO.endPoint}")
    private String endPoint;
    @Value("${minIO.accessKey}")
    private String accessKey;
    @Value("${minIO.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endPoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
