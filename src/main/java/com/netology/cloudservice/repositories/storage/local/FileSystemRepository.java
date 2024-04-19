package com.netology.cloudservice.repositories.storage.local;

import com.netology.cloudservice.exceptions.StorageException;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;

public interface FileSystemRepository {
    FileSystemResource findInFileSystem(String location);
    String save(byte[] content, String fileName) throws IOException;
    boolean remove(String location) throws StorageException;
}
