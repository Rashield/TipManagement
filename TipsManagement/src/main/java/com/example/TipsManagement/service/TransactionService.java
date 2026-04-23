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
import com.example.TipsManagement.model.enums.Status;
import com.example.TipsManagement.model.enums.TransactionType;
import com.example.TipsManagement.repository.IBancaRepository;
import com.example.TipsManagement.repository.ITransactionRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
    public Transaction openBet(Bet bet){
        bet.getBanca().withdraw(bet.getStake());
        Transaction transaction = createTransaction(bet.getBanca(), bet.getStake(),TransactionType.BET);
        transaction.setBet(bet);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction betWin(Bet bet){
        bet.getBanca().deposit(bet.getTotalValue());
        Transaction transaction = createTransaction(bet.getBanca(), bet.getTotalValue(), TransactionType.BET_WIN);
        transaction.setBet(bet);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public void betVoid(Bet bet){
        bet.getBanca().deposit(bet.getStake());
        Transaction transaction = createTransaction(bet.getBanca(), bet.getStake(),TransactionType.BET_VOID);
        transaction.setBet(bet);
        transactionRepository.save(transaction);
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

    @Transactional
    public void deleteTransactionFromBet(Bet bet, Status oldStatus){
        //se for repassada uma bet com status Green, busca a transação de WIN e apaga somente ela, deixando a transacao de retirada criada ao salvar a Bet
        if(oldStatus.equals(Status.GREEN)){
            List<Transaction> transactions = transactionRepository.findByBetIdAndTransactionType(bet.getId(), TransactionType.BET_WIN);
            for (Transaction transaction : transactions) {

                Banca banca = transaction.getBanca();

                // reverte o saldo (era um crédito)
                banca.withdraw(transaction.getAmount());

                transactionRepository.delete(transaction);
            }
        }
        if(oldStatus.equals(Status.VOID)){
            List<Transaction> transactions = transactionRepository.findByBetIdAndTransactionType(bet.getId(), TransactionType.BET_VOID);
            for (Transaction transaction : transactions) {

                Banca banca = transaction.getBanca();

                // reverte o saldo (era um crédito)
                banca.withdraw(transaction.getAmount());

                transactionRepository.delete(transaction);
            }
        }

    }




    //metodo para criar uma transação, será usada em todas transações, visa evitar duplicação
    private Transaction createTransaction(Banca banca, BigDecimal amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setBanca(banca);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setDate(LocalDate.now());
        return transaction;
    }

//    private Transaction getOwnedTransaction(Long userId, Long transactionId) {
//        return transactionRepository
//                .findByIdAndBanca_BetHouse_UsuarioId(transactionId, userId)
//                .orElseThrow(() -> new NotFoundException("Transação não encontrada"));
//    }

    //public List<TransferResponse> listAllTransactions
}
