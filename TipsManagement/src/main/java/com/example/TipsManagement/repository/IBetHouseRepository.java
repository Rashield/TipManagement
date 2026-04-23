package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.BetHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBetHouseRepository extends JpaRepository<BetHouse, Long> {
    boolean existsByUsuarioIdAndName(Long userId, String name);
    List<BetHouse> findAllByUsuarioId(Long userId);
    Optional<BetHouse> findByIdAndUsuarioId(Long betHouseId, Long userId);
}
