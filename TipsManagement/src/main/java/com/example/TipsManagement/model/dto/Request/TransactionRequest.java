package com.example.TipsManagement.model.dto.Request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private BigDecimal amount;
}
