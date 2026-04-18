package com.example.TipsManagement.service;

import com.example.TipsManagement.mapper.BetMapper;
import com.example.TipsManagement.model.Banca;
import com.example.TipsManagement.model.Bet;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.model.dto.Request.BetRequest;
import com.example.TipsManagement.model.dto.Response.BetResponse;
import com.example.TipsManagement.model.enums.Status;
import com.example.TipsManagement.repository.IBetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BetService {

    private final IBetRepository betRepository;
    private final BancaService bancaService;
    private final TipsterService tipsterService;
    private final UnitValueService unitValueService;
    private final BetMapper betMapper;
    private final TransactionService transactionService;

    public BetService(IBetRepository betRepository, TransactionService transactionService, BancaService bancaService, TipsterService tipsterService, UnitValueService unitValueService, BetMapper betMapper, TransactionService transactionService1) {
        this.betRepository = betRepository;
        this.bancaService = bancaService;
        this.tipsterService = tipsterService;
        this.unitValueService = unitValueService;
        this.betMapper = betMapper;
        this.transactionService = transactionService1;
    }

    public BetResponse save(Long userId, BetRequest betRequest){
        Banca betBanca = bancaService.getOwnedBanca(userId, betRequest.getBancaId());
        Tipster tipster = tipsterService.getOwnedTipster(userId,betRequest.getTipsterId());
        Bet bet = createBet(userId, betBanca, tipster, betRequest);
        betRepository.save(bet);
        processBetFinancials(bet);
        return betMapper.toResponse(bet);
    }

    private Bet createBet(Long userId, Banca banca, Tipster tipster, BetRequest betRequest){
        Bet bet = new Bet();

        Usuario usuario = new Usuario();
        usuario.setId(userId);
        bet.setUsuario(usuario);


        bet.setBanca(banca);
        bet.setTipster(tipster);
        bet.setSport(betRequest.getSport());
        bet.setOdd(betRequest.getOdd());
        //se status recebido for null define como pendente
        Status status = betRequest.getStatus() != null
                ? betRequest.getStatus()
                : Status.PENDING;

        bet.setStatus(status);

        bet.setHomeTeam(betRequest.getHomeTeam());
        bet.setAwayTeam(betRequest.getAwayTeam());
        bet.setDescription(betRequest.getDescription());
        bet.setMatchDate(betRequest.getMatchDate());
        bet.setUnit(betRequest.getUnit());
        bet.setUnitValue(unitValueService.getCurrentUnit(userId).getUnitValue());
        bet.setStake(bet.getUnit().multiply(bet.getUnitValue()));
        bet.setProfit(calculateProfit(bet));
        bet.setResultValue(calculateResultValue(bet));

        return bet;
    }

    private BigDecimal calculateProfit(Bet bet) {

        if (bet.getStatus() == Status.GREEN) {
            return bet.getStake()
                    .multiply(bet.getOdd().subtract(BigDecimal.ONE));
        }

        if (bet.getStatus() == Status.RED) {
            return bet.getStake().negate();
        }

        return BigDecimal.ZERO;
    }
    private BigDecimal calculateResultValue(Bet bet) {

        if (bet.getStatus() == Status.GREEN) {
            return bet.getStake().multiply(bet.getOdd());
        }
        return BigDecimal.ZERO;
    }
    private void processBetFinancials(Bet bet){
        // Sempre retira o valor da aposta do saldo
        transactionService.createBetTransaction(bet);

        // Se for cadastrada como green deposita o lucro
        if (bet.getStatus() == Status.GREEN) {
            transactionService.betWin(bet);
        }
    }
}
