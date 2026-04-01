package com.example.TipsManagement.mapper;


import com.example.TipsManagement.model.Transaction;
import com.example.TipsManagement.model.dto.Response.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private final BancaMapper bancaMapper;

    public TransactionMapper(BancaMapper bancaMapper) {
        this.bancaMapper = bancaMapper;
    }

    public TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setTransactionType(transaction.getTransactionType());
        response.setDate(transaction.getDate());

        response.setBancaResponse(bancaMapper.toResponse(transaction.getBanca()));

        return response;
    }
}
