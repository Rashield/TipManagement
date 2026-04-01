package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.mapper.TransactionMapper;
import com.example.TipsManagement.model.Banca;
import com.example.TipsManagement.model.Transaction;
import com.example.TipsManagement.model.dto.Request.TransactionRequest;
import com.example.TipsManagement.model.dto.Response.TransactionResponse;
import com.example.TipsManagement.model.enums.TransactionType;
import com.example.TipsManagement.repository.IBancaRepository;
import com.example.TipsManagement.repository.ITransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionService {
    private final ITransactionRepository transactionRepository;
    private final IBancaRepository bancaRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(ITransactionRepository transactionRepository, IBancaRepository bancaRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.bancaRepository = bancaRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public TransactionResponse deposit(Long userId, Long bancaId, TransactionRequest transactionRequest){
        Banca banca = bancaRepository.findByIdAndBetHouse_UsuarioId(bancaId, userId)
                        .orElseThrow(()->
                            new NotFoundException("Não existe Banca com os parâmetros fornecidos."));

        banca.setBalance(banca.getBalance().add(transactionRequest.getAmount()));
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setDate(LocalDate.now());
        transaction.setAmount(transactionRequest.getAmount());
        banca.setTotalDeposit(banca.getTotalDeposit().add(transactionRequest.getAmount()));
        transaction.setBanca(banca);

        Transaction savedTransaction = transactionRepository.save(transaction);


        return transactionMapper.toResponse(savedTransaction);
    }
}
