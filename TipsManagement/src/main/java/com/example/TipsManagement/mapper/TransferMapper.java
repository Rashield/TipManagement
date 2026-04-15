package com.example.TipsManagement.mapper;

import com.example.TipsManagement.model.Banca;
import com.example.TipsManagement.model.Transaction;
import com.example.TipsManagement.model.dto.Response.TransferResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransferMapper {
    private final TransactionMapper transactionMapper;

    public TransferMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    public TransferResponse toResponse(UUID transferId, Transaction transferWithdraw, Transaction depositWithdraw){
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setTransferId(transferId);
        transferResponse.setFromTransaction(transactionMapper.toResponse(transferWithdraw));
        transferResponse.setToTransaction(transactionMapper.toResponse(depositWithdraw));

        return transferResponse;
    }
}
