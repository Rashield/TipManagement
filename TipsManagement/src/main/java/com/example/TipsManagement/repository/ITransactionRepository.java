package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.Transaction;
import com.example.TipsManagement.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByIdAndBanca_BetHouse_UsuarioId(Long transactionId, Long userId);
    List<Transaction> findByBetId(Long betId);
    List<Transaction> findByBetIdAndTransactionType(Long betId, TransactionType type);
}
