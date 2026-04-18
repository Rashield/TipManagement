package com.example.TipsManagement.mapper;

import com.example.TipsManagement.model.Bet;
import com.example.TipsManagement.model.dto.Response.BetResponse;
import org.springframework.stereotype.Component;

@Component
public class BetMapper {

    private final BancaMapper bancaMapper;
    private final TipsterMapper tipsterMapper;

    public BetMapper(BancaMapper bancaMapper, TipsterMapper tipsterMapper) {
        this.bancaMapper = bancaMapper;
        this.tipsterMapper = tipsterMapper;
    }

    public BetResponse toResponse(Bet bet){
        BetResponse betResponse = new BetResponse();
        betResponse.setBetId(bet.getId());
        betResponse.setBanca(bancaMapper.toResponse(bet.getBanca()));
        betResponse.setTipster(tipsterMapper.toResponse(bet.getTipster()));
        betResponse.setSport(bet.getSport());
        betResponse.setOdd(bet.getOdd());
        betResponse.setStatus(bet.getStatus());
        betResponse.setHomeTeam(bet.getHomeTeam());
        betResponse.setAwayTeam(bet.getAwayTeam());
        betResponse.setDescription(bet.getDescription());
        betResponse.setMatchDate(bet.getMatchDate());
        betResponse.setUnit(bet.getUnit());
        betResponse.setUnitValue(bet.getUnitValue());
        betResponse.setStake(bet.getStake());
        betResponse.setProfit(bet.getProfit());
        betResponse.setResultValue(bet.getResultValue());

        return betResponse;
    }
}
