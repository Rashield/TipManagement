package com.example.TipsManagement.model.dto.Response;

import com.example.TipsManagement.model.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDate date;
    private BancaResponse bancaResponse;
}
