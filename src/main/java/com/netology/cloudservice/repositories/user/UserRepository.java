package com.netology.cloudservice.repositories.user;

import com.netology.cloudservice.models.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Transactional
    @Modifying
    @Query("update User u set u.lastEnter = :lastEnter WHERE u.id = :userId")
    void setLastEnter(@Param("userId") Long userId, @Param("lastEnter") Long lastEnter);
}
