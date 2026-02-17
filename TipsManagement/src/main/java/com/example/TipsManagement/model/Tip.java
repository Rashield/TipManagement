package com.example.TipsManagement.model;

import com.example.TipsManagement.enums.Result;
import com.example.TipsManagement.enums.TipMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "CasaAposta_id")
    private CasaAposta house;

    @ManyToOne
    @JoinColumn (name = "Tipster_id")
    private Tipster tipster;

    @ManyToOne
    @JoinColumn(name = "Sport_id")
    private Sport sport;

    @NotNull
    private BigDecimal odd;

    @Enumerated(EnumType.STRING)
    private Result result;

    private String teamA;

    private String teamB;

    @Enumerated(EnumType.STRING)
    private TipMethod tipMethod;

    @NotNull
    private LocalDate date;

}
