package com.example.TipsManagement.model.dto.Request;

import com.example.TipsManagement.model.BetHouse;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.model.enums.Result;
import com.example.TipsManagement.model.enums.Sport;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BetRequest {

    private BetHouse betHouse;

    private Tipster tipster;

    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Sport sport;

    private BigDecimal odd;

    @Enumerated(EnumType.STRING)
    private Result result;

    private String teamA;

    private String teamB;

    private String description;

//    @Enumerated(EnumType.STRING)
//    private TipMethod tipMethod;

    private LocalDate date;

    @NotNull
    private BigDecimal unit; // Ex: 1U, 2.5U

}
