package com.example.TipsManagement.model.dto.Response;

import lombok.Data;

import java.util.UUID;

@Data
public class TransferResponse {
    private UUID transferId;
    private TransactionResponse fromTransaction;
    private TransactionResponse toTransaction;
}
