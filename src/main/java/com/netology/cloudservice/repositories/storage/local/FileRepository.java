package com.netology.cloudservice.repositories.storage.local;

import com.netology.cloudservice.models.File;
import com.netology.cloudservice.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> getFileByNameAndStatus(String name, Status status);

    List<File> findFilesByStatus(Status status);

    List<File> findFilesByUserLoginAndStatus(String login, Status status);
}
