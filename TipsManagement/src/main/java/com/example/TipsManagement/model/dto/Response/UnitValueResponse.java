package com.example.TipsManagement.model.dto.Response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UnitValueResponse {
    private Long id;
    private BigDecimal unitValue;
    private LocalDate startDate;
}
