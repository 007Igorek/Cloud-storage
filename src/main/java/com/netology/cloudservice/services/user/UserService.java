package com.netology.cloudservice.services.user;

import com.netology.cloudservice.models.security.User;

import java.util.List;

public interface UserService {

    User findByLogin(String login);

    List<User> getAllUsers();

    User getById(Long id);

    User register(User user);

    void setLastEnter(Long userId, Long lastEnter);

    User update(User user);

    void delete(Long id);
}
