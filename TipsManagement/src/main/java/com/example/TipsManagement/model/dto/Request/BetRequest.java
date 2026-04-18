package com.example.TipsManagement.model.dto.Request;

import com.example.TipsManagement.model.BetHouse;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.model.enums.Sport;
import com.example.TipsManagement.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BetRequest {

    @NotNull
    private Long bancaId;

    @NotNull
    private Long tipsterId;

    private Sport sport;

    @Positive
    @NotNull
    private BigDecimal odd;

    private Status status;

    private String homeTeam;

    private String awayTeam;

    private String description;

//    @Enumerated(EnumType.STRING)
//    private TipMethod tipMethod;

    private LocalDate matchDate;

    @NotNull
    @Positive
    private BigDecimal unit;

}
