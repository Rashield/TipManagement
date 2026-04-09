package com.example.TipsManagement.model;

import com.example.TipsManagement.model.enums.Result;
import com.example.TipsManagement.model.enums.Sport;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "BetHouse_id")
    private BetHouse betHouse;

    @ManyToOne
    @JoinColumn (name = "Tipster_id")
    private Tipster tipster;

    @ManyToOne
    @JoinColumn (name = "Usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Sport sport;

    @Column(nullable = false)
    private BigDecimal odd;

    @Enumerated(EnumType.STRING)
    private Result result;

    private BigDecimal resultValue; //deve ser unitValue * unit * odd

    private String teamA;

    private String teamB;

    private String description;

//    @Enumerated(EnumType.STRING)
//    private TipMethod tipMethod;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal unit; // Ex: 1U, 2.5U

    @Column(nullable = false)
    private BigDecimal unitValue; // VALOR em R$ na data


}
