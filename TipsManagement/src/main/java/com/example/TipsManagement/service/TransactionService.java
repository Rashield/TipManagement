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

import java.math.BigDecimal;
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
        Banca banca = getBanca(bancaId, userId); // busca banca fornecida
        banca.deposit(transactionRequest.getAmount()); //chama o metodo de deposito da entidade Banca para depositar o valor
        Transaction transaction = createTransaction(banca, transactionRequest.getAmount(), TransactionType.DEPOSIT);//chama a funcao para criar uma transacao
        return transactionMapper.toResponse(transactionRepository.save(transaction));//converte a transacao para Response
    }

    @Transactional
    public TransactionResponse withdraw(Long userId, Long bancaId, TransactionRequest transactionRequest){
        Banca banca = getBanca(bancaId, userId);
        banca.withdraw(transactionRequest.getAmount());
        Transaction transaction = createTransaction(banca,transactionRequest.getAmount(),TransactionType.WITHDRAW);
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }
    //metodo para validar se a banca existe, vai ser usado em todas transações
    private Banca getBanca(Long bancaId, Long userId) {
        return bancaRepository.findByIdAndBetHouse_UsuarioId(bancaId, userId)
                .orElseThrow(() ->
                        new NotFoundException("Não existe Banca com os parâmetros fornecidos."));
    }
    //metodo para criar uma transação, tbm será usada em todas, visa evitar duplicação
    private Transaction createTransaction(Banca banca, BigDecimal amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setBanca(banca);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setDate(LocalDate.now());
        return transaction;
    }
}
