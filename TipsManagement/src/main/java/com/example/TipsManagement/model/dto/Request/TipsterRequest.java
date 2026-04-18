package com.example.TipsManagement.model.dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipsterRequest {

    private String name;


}
