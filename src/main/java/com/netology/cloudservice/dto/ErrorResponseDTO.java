package com.netology.cloudservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorResponseDTO {
    private final int id;
    private final String message;
}
