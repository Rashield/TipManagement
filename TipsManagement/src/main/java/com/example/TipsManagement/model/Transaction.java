package com.example.TipsManagement.model;

import com.example.TipsManagement.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDate date;

    @ManyToOne
    private Banca banca;

    @ManyToOne
    @JoinColumn(name = "bet_id")
    private Bet bet;

    private UUID transferId;
}
