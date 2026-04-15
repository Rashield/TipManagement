package com.example.TipsManagement.model.dto.Request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private Long fromBancaId;
    private Long toBancaId;
    private BigDecimal amount;
}
