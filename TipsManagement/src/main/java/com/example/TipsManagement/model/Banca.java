package com.example.TipsManagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Banca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    private BigDecimal totalDeposit;

    private BigDecimal totalWithdraw;

    @OneToOne
    @JoinColumn(name = "bethouse_id")
    private BetHouse betHouse;

}
