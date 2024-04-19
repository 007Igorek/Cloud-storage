package com.netology.cloudservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@AllArgsConstructor
@Data
@Builder
public class FileResponseDTO {

    @JsonProperty("filename")
    private String filename;

    @JsonProperty("size")
    private Long size;

}
