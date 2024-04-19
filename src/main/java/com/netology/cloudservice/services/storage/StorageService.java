package com.netology.cloudservice.services.storage;

import com.netology.cloudservice.exceptions.StorageException;
import com.netology.cloudservice.models.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface StorageService {

    Long save(MultipartFile multipartFile, String fileName, String originalName) throws StorageException;

    Resource getByName(String filename) throws FileNotFoundException, StorageException;

    List<File> getFiles();

    String setNewFilename(String fileName, String newFileName) throws FileNotFoundException;

    void delete(String filename) throws FileNotFoundException;

    void clear(String filename) throws StorageException;
}
