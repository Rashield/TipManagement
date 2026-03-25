package com.example.TipsManagement.model.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BetHouseRequest {

    @NotNull
    private String name;
}
