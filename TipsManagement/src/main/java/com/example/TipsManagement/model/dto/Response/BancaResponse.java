package com.example.TipsManagement.model.dto.Response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BancaResponse {
    private Long id;
    private BigDecimal balance;
    private BetHouseResponse betHouse;
}
