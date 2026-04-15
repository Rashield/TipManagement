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
        response.setTransactionId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setTransactionType(transaction.getTransactionType());
        response.setDate(transaction.getDate());
        //ainda é preciso adicionar o mapper de Bet para BetSummaryResponse
        response.setBanca(bancaMapper.toResponse(transaction.getBanca()));

        return response;
    }

}
