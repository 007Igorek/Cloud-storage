package com.netology.cloudservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@AllArgsConstructor
@Data
@Builder
public class AuthResponseDTO {
    @NotBlank
    @JsonProperty("auth-token")
    private String token;
}
