package com.example.TipsManagement.model.dto.Response;

import com.example.TipsManagement.model.Bet;
import com.example.TipsManagement.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private Long transactionId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDate date;
    private BancaResponse banca;
    private Bet bet;
}
