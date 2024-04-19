package com.netology.cloudservice.services.storage;

import com.netology.cloudservice.exceptions.StorageException;
import com.netology.cloudservice.models.File;
import com.netology.cloudservice.models.Status;
import com.netology.cloudservice.models.security.User;
import com.netology.cloudservice.repositories.storage.local.FileRepository;
import com.netology.cloudservice.repositories.storage.remote.MinioRepository;
import com.netology.cloudservice.repositories.storage.remote.MinioRepositoryImpl;
import com.netology.cloudservice.repositories.user.UserRepository;
import com.netology.cloudservice.services.user.UserService;
import com.netology.cloudservice.utils.FileUtil;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
@Primary
@Slf4j
public class MinioService extends BaseStorageService implements StorageService {

    private final MinioRepository minioRepository;
    private final FileRepository fileRepository;

    private final UserService userService;


    @Autowired
    public MinioService(UserRepository userRepository, FileRepository fileRepository, MinioRepository minioRepository,
                        FileRepository fileRepository1, UserService userService) {
        super(userRepository, fileRepository);
        this.minioRepository = minioRepository;
        this.fileRepository = fileRepository1;
        this.userService = userService;
    }

    // create
    @Override
    public Long save(MultipartFile multipartFile, String fileName, String originalName) throws StorageException {
        User user = getUser();
        String userFolder = ((MinioRepositoryImpl) minioRepository).getUserFolder(user.getId());
        String location = Paths.get(userFolder, originalName).toString();

        File file;

        try {
            minioRepository.uploadFile(user.getId(), originalName, multipartFile);

            byte[] bytes = multipartFile.getBytes();
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

        } catch (MinioException | IOException e) {
            throw new StorageException(e.getMessage());
        }

        File fileDb = fileRepository.save(file);
        return fileDb.getId();
    }

    // read
    @Override
    public Resource getByName(String filename) throws FileNotFoundException, StorageException {
        File file = fileRepository.getFileByNameAndStatus(filename, Status.ACTIVE).orElseThrow(FileNotFoundException::new);

        InputStream inputStream;

        try {
            inputStream = minioRepository.findInStorage(getUser().getId(), file.getOriginalName());
        } catch (MinioException e) {
            throw new StorageException(e.getMessage());
        }

        return new InputStreamResource(inputStream);
    }

    public Resource findById(Long id) throws FileNotFoundException {
        File file = fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
        InputStream inputStream;

        try {
            inputStream = minioRepository.findInStorage(getUser().getId(), file.getOriginalName());
        } catch (MinioException e) {
            throw new FileNotFoundException(e.getMessage());
        }

        return new InputStreamResource(inputStream);
    }

    // delete
    @Override
    public void clear(String filename) throws StorageException {
        List<File> files = fileRepository.findFilesByStatus(Status.DELETED);

        try {
            for (File file : files) {
                if (minioRepository.delete(file.getLocation())) {
                    fileRepository.delete(file);
                }
            }
        } catch (MinioException e) {
            throw new StorageException(e.getMessage());
        }
    }

    // other
    @EventListener(ApplicationReadyEvent.class)
    public void createFoldersByUsers() throws MinioException {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            if (!minioRepository.checkIfUserFolderExist(user.getId())) {
                createUserInitialFolder(user.getId());
            } else {
                log.debug("User's {} folder already exist", user.getLogin());
            }
        }
    }

    private void createUserInitialFolder(long userId) throws MinioException {
        minioRepository.createFolder(userId, "/" + FOLDER_STATIC_FILE_NAME);
    }

}
