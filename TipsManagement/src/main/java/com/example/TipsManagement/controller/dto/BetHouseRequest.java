package com.example.TipsManagement.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BetHouseRequest {

    @NotNull
    private String name;
}
