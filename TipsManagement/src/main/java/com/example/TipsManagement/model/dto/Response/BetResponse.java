package com.example.TipsManagement.model.dto.Response;

import com.example.TipsManagement.model.enums.Sport;
import com.example.TipsManagement.model.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BetResponse {

    private long betId;
    private BancaResponse banca;
    private TipsterResponse tipster;
    private Sport sport;
    private BigDecimal odd;
    private Status status;
    private String homeTeam;
    private String awayTeam;
    private String description;
    private LocalDate matchDate;
    private BigDecimal unit;
    private BigDecimal unitValue;
    private BigDecimal stake;
    private BigDecimal profit;
    private BigDecimal totalValue;
}
