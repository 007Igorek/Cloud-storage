package com.netology.cloudservice.services.security;

import com.netology.cloudservice.dto.AuthRequestDTO;
import com.netology.cloudservice.dto.AuthResponseDTO;

import javax.servlet.http.HttpServletRequest;

public interface JWTService {

    AuthResponseDTO login(AuthRequestDTO authRequestDTO);

    void logout(HttpServletRequest request);
}
