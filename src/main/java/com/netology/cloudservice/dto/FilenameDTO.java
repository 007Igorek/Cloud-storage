package com.netology.cloudservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FilenameDTO {
    @NotBlank
    @JsonProperty("name")
    private String name;
}
