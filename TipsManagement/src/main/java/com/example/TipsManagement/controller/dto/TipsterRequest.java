package com.example.TipsManagement.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipsterRequest {

    @NotNull
    private String name;


}
