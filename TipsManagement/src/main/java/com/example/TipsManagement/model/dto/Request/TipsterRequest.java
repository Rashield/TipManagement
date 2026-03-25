package com.example.TipsManagement.model.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipsterRequest {

    @NotNull
    private String name;


}
