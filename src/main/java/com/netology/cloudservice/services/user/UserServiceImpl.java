package com.netology.cloudservice.services.user;

import com.netology.cloudservice.models.Status;
import com.netology.cloudservice.models.security.User;
import com.netology.cloudservice.repositories.user.RoleRepository;
import com.netology.cloudservice.repositories.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_ROLE_NAME = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login).orElse(null);
        log.info("{} find by username {}", user == null ? null : user.getId(), login);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        log.info("User {} by id: {}", result == null ? "didn't find" : "found", id);
        return result;
    }

    @Override
    public User register(User user) {
        user.setRoles(Collections.singletonList(roleRepository.findByName(DEFAULT_USER_ROLE_NAME)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        User userDb = userRepository.save(user);
        log.info("{} registered!", userDb.getId());
        return userDb;
    }

    @Override
    public void setLastEnter(Long userId, Long lastEnter) {
        userRepository.setLastEnter(userId, lastEnter);
    }

    @Override
    public User update(User user) {
        if (user.getRoles().size() == 0) {
            user.setRoles(Collections.singletonList(roleRepository.findByName(DEFAULT_USER_ROLE_NAME)));
        }

        log.info("{} updated!", user.getId());

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("{} deleted!", id);
    }
}
