package com.example.TipsManagement.model;

import com.example.TipsManagement.Exception.BadRequestException;
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

    @Column(nullable = false)
    private BigDecimal totalDeposit = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal totalWithdraw = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "bethouse_id")
    private BetHouse betHouse;

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        this.totalDeposit = this.totalDeposit.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new BadRequestException("Saldo insuficiente.");
        }
        this.balance = this.balance.subtract(amount);
        this.totalWithdraw = this.totalWithdraw.add(amount);
    }
}
