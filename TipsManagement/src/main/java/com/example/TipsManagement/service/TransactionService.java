package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BusinessException;
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
    private final BancaService bancaService;
    private final TransactionMapper transactionMapper;
    private final TransferMapper transferMapper;

    public TransactionService(ITransactionRepository transactionRepository, IBancaRepository bancaRepository, BancaService bancaService, TransactionMapper transactionMapper, TransferMapper transferMapper) {
        this.transactionRepository = transactionRepository;
        this.bancaRepository = bancaRepository;
        this.bancaService = bancaService;
        this.transactionMapper = transactionMapper;
        this.transferMapper = transferMapper;
    }

    @Transactional
    public TransactionResponse deposit(Long userId, Long bancaId, TransactionRequest transactionRequest){
        Banca banca = bancaService.getOwnedBanca(bancaId, userId); // busca banca fornecida
        banca.deposit(transactionRequest.getAmount()); //chama o metodo de deposito da entidade Banca para depositar o valor
        Transaction transaction = createTransaction(banca, transactionRequest.getAmount(), TransactionType.DEPOSIT);//chama a funcao para criar uma transacao
        return transactionMapper.toResponse(transactionRepository.save(transaction));//converte a transacao para Response
    }

    @Transactional
    public TransactionResponse withdraw(Long userId, Long bancaId, TransactionRequest transactionRequest){
        Banca banca = bancaService.getOwnedBanca(bancaId, userId);
        banca.withdraw(transactionRequest.getAmount());
        Transaction transaction = createTransaction(banca,transactionRequest.getAmount(),TransactionType.WITHDRAW);
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }


    @Transactional
    public Transaction createBetTransaction(Bet bet){
        bet.getBanca().withdraw(bet.getStake());
        Transaction transaction = createTransaction(bet.getBanca(), bet.getStake(),TransactionType.BET);
        transaction.setBet(bet);
        return transactionRepository.save(transaction);
    }

    @Transactional Transaction betWin(Bet bet){
        bet.getBanca().deposit(bet.getResultValue());
        Transaction transaction = createTransaction(bet.getBanca(), bet.getResultValue(), TransactionType.BET_WIN);
        transaction.setBet(bet);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public TransferResponse transfer(Long userId, TransferRequest transferRequest){
        //validações
        Banca toBanca = bancaService.getOwnedBanca(transferRequest.getToBancaId(), userId);
        Banca fromBanca = bancaService.getOwnedBanca(transferRequest.getFromBancaId(), userId);
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



    //metodo para criar uma transação, será usada em todas tranasções, visa evitar duplicação
    private Transaction createTransaction(Banca banca, BigDecimal amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setBanca(banca);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setDate(LocalDate.now());
        return transaction;
    }
}
