package com.netology.cloudservice.config.storage;

import com.netology.cloudservice.exceptions.StorageException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Configuration
public class StorageConfig {

    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.access_key}")
    private String accessKey;
    @Value("${minio.secret_key}")
    private String secretKey;
    @Value("${minio.bucket}")
    private String bucket;

    @Bean
    public MinioClient minioClient() throws StorageException {
        var client = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();

        try {
            boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());

            if (!found) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            } else {
                log.info("Bucket '" + bucket + "' already exists.");
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException
                 | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException
                 | XmlParserException e) {
            throw new StorageException(e.getMessage());
        }

        return client;
    }
}
