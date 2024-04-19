package com.netology.cloudservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@AllArgsConstructor
@Data
@Builder
public class AuthRequestDTO {
    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
