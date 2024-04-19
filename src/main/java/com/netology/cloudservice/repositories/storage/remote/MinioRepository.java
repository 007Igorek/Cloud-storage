package com.netology.cloudservice.repositories.storage.remote;

import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioRepository {

    // create
    void uploadFile(long userId, String rawFullPath, MultipartFile file) throws MinioException;

    // read
    InputStream findInStorage(Long userId, String filename) throws MinioException;

    // update

    // delete
    boolean delete(String path) throws MinioException;

    // other
    void createFolder(long userId, String rawPath) throws MinioException;

    boolean checkIfUserFolderExist(long userId) throws MinioException;
}
