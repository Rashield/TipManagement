package com.example.TipsManagement.model;

import com.example.TipsManagement.model.enums.Result;
import com.example.TipsManagement.model.enums.Sport;
import com.example.TipsManagement.model.enums.TipMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private Sport sport;

    @Column(nullable = false)
    private BigDecimal odd;

    @Enumerated(EnumType.STRING)
    private Result result;

    private BigDecimal resultValue;

    private String teamA;

    private String teamB;

    @Enumerated(EnumType.STRING)
    private TipMethod tipMethod;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal unit; // Ex: 1U, 2.5U

    @Column(nullable = false)
    private BigDecimal unitValue; // VALOR em R$ na data



}
