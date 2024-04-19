package com.netology.cloudservice.services.storage;

import com.netology.cloudservice.exceptions.StorageException;
import com.netology.cloudservice.models.File;
import com.netology.cloudservice.models.Status;
import com.netology.cloudservice.repositories.storage.local.FileRepository;
import com.netology.cloudservice.repositories.storage.local.FileSystemRepository;
import com.netology.cloudservice.repositories.user.UserRepository;
import com.netology.cloudservice.utils.FileUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Profile("local")
public class FileSystemStorageServiceImpl extends BaseStorageService implements StorageService {

    private final FileSystemRepository fileSystemRepository;
    private final FileRepository fileRepository;


    public FileSystemStorageServiceImpl(UserRepository userRepository, FileRepository fileRepository, FileSystemRepository fileSystemRepository, FileRepository fileRepository1) {
        super(userRepository, fileRepository);
        this.fileSystemRepository = fileSystemRepository;
        this.fileRepository = fileRepository1;
    }

    // create
    @Override
    public Long save(MultipartFile multipartFile, String fileName, String originalName) throws StorageException {

        File file;

        try {
            byte[] bytes = multipartFile.getBytes();
            String location = fileSystemRepository.save(bytes, fileName);
            file = new File(
                    originalName,
                    fileName,
                    FileUtil.getSizeByBytes(bytes),
                    FileUtil.bytesToHumanString(bytes.length),
                    location,
                    new Date(),
                    new Date(),
                    Status.ACTIVE,
                    getUser()
            );

        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }

        File fileDb = fileRepository.save(file);
        return fileDb.getId();
    }

    // read
    @Override
    public Resource getByName(String filename) throws FileNotFoundException {
        File file = fileRepository.getFileByNameAndStatus(filename, Status.ACTIVE).orElseThrow(FileNotFoundException::new);
        return fileSystemRepository.findInFileSystem(file.getLocation());
    }

    public FileSystemResource findById(Long id) throws FileNotFoundException {
        File file = fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
        return fileSystemRepository.findInFileSystem(file.getLocation());
    }

    // update


    // delete
    @Override
    public void clear(String filename) throws StorageException {
        List<File> files = fileRepository.findFilesByStatus(Status.DELETED);
        for (File file : files) {
            if (fileSystemRepository.remove(file.getLocation())) {
                fileRepository.delete(file);
            }
        }
    }
}
