package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBetRepository extends JpaRepository<Bet, Long> {
}
