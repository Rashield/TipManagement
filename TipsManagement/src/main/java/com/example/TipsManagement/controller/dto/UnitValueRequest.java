package com.example.TipsManagement.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UnitValueRequest {

    @NotNull
    private BigDecimal unitValue;

}
