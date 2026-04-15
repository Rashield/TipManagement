package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.mapper.TransactionMapper;
import com.example.TipsManagement.mapper.TransferMapper;
import com.example.TipsManagement.model.Banca;
import com.example.TipsManagement.model.Bet;
import com.example.TipsManagement.model.Transaction;
import com.example.TipsManagement.model.dto.Request.TransactionRequest;
import com.example.TipsManagement.model.dto.Request.TransferRequest;
import com.example.TipsManagement.model.dto.Response.TransactionResponse;
import com.example.TipsManagement.model.dto.Response.TransferResponse;
import com.example.TipsManagement.model.enums.TransactionType;
import com.example.TipsManagement.repository.IBancaRepository;
import com.example.TipsManagement.repository.ITransactionRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionService {
    private final ITransactionRepository transactionRepository;
    private final IBancaRepository bancaRepository;
    private final TransactionMapper transactionMapper;
    private final TransferMapper transferMapper;

    public TransactionService(ITransactionRepository transactionRepository, IBancaRepository bancaRepository, TransactionMapper transactionMapper, TransferMapper transferMapper) {
        this.transactionRepository = transactionRepository;
        this.bancaRepository = bancaRepository;
        this.transactionMapper = transactionMapper;
        this.transferMapper = transferMapper;
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

    @Transactional
    public TransactionResponse betTransaction(Long userId, Long bancaId, Bet bet){
        Banca banca = getBanca(bancaId, userId);
        banca.withdraw(bet.getUnitValue());
        Transaction transaction = createTransaction(banca,bet.getUnitValue(),TransactionType.BET);
        transaction.setBet(bet);
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

    @Transactional
    public TransferResponse transfer(Long userId, TransferRequest transferRequest){
        //validações
        Banca toBanca = getBanca(transferRequest.getToBancaId(), userId);
        Banca fromBanca = getBanca(transferRequest.getFromBancaId(), userId);
        if (toBanca.equals(fromBanca)) {
            throw new BusinessException("Não pode transferir para a mesma banca");
        }
        //saque e deposito
        fromBanca.withdraw(transferRequest.getAmount());
        Transaction transferWithdraw = createTransaction(fromBanca,transferRequest.getAmount(),TransactionType.WITHDRAW);
        toBanca.deposit(transferRequest.getAmount());
        Transaction transferdeposit = createTransaction(toBanca,transferRequest.getAmount(),TransactionType.DEPOSIT);

        //seta o id da transferencia para ambos e salva ações em banco
        UUID transferId = UUID.randomUUID();
        transferWithdraw.setTransferId(transferId);
        transferdeposit.setTransferId(transferId);
        transactionRepository.save(transferWithdraw);
        transactionRepository.save(transferdeposit);
        //chama o mapper para converter e retornar um JSON organizado
        return transferMapper.toResponse(transferId, transferWithdraw, transferdeposit);

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
