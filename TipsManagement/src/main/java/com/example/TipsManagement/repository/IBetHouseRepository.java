package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.BetHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBetHouseRepository extends JpaRepository<BetHouse, Long> {
    boolean existsByName(String name);
}
