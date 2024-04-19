package com.netology.cloudservice.services.security;

import com.netology.cloudservice.models.security.JwtUser;
import com.netology.cloudservice.models.security.User;
import com.netology.cloudservice.services.user.UserService;
import com.netology.cloudservice.utils.JWTUserAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JWTUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JWTUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User with login" + login + " not found!");
        }

        JwtUser jwtUser = JWTUserAdapter.create(user);
        log.info("User with login: {} successfully loaded!", login);
        return jwtUser;
    }
}
