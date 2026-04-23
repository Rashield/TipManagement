package com.example.TipsManagement.model;

import com.example.TipsManagement.model.enums.Sport;
import com.example.TipsManagement.model.enums.Status;
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
    @JoinColumn (name = "Banca_id")
    private Banca banca;

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
    private Status status;

    private String homeTeam;

    private String awayTeam;

    private String description;

//    @Enumerated(EnumType.STRING)
//    private TipMethod tipMethod;

    private LocalDate matchDate;

    @Column(nullable = false)
    private BigDecimal unit; // Ex: 1U, 2.5U

    @Column(nullable = false)
    private BigDecimal unitValue;// VALOR em R$ na data

    private BigDecimal stake; //unit * unitvalue

    private BigDecimal profit; //stake * (odd -1)
    private BigDecimal totalValue; //deve ser STAKE * odd
}
