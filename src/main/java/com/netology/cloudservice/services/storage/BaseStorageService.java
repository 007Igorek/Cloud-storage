package com.netology.cloudservice.services.storage;

import com.netology.cloudservice.models.File;
import com.netology.cloudservice.models.Status;
import com.netology.cloudservice.models.security.User;
import com.netology.cloudservice.repositories.storage.local.FileRepository;
import com.netology.cloudservice.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BaseStorageService {

    public static final String FOLDER_STATIC_FILE_NAME = ".folder.ini";

    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Autowired
    public BaseStorageService(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    // read
    public List<File> getFiles() {
        List<File> files = fileRepository.findFilesByUserLoginAndStatus(getUser().getLogin(), Status.ACTIVE);

        if (files == null) {
            return new ArrayList<>();
        }

        return files;
    }

    // update
    public String setNewFilename(String fileName, String newFileName) throws FileNotFoundException {
        File file = fileRepository.getFileByNameAndStatus(fileName, Status.ACTIVE).orElseThrow(FileNotFoundException::new);
        file.setName(newFileName);
        fileRepository.save(file);
        return file.getName();
    }

    // delete
    public void delete(String filename) throws FileNotFoundException {
        File file = fileRepository.getFileByNameAndStatus(filename, Status.ACTIVE).orElseThrow(FileNotFoundException::new);
        file.setStatus(Status.DELETED);
        file.setUpdated(new Date());
        fileRepository.save(file);
    }

    // other
    protected User getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
