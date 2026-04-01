package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.Banca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBancaRepository extends JpaRepository<Banca, Long> {
    List<Banca> findAllByUsuarioId(Long userId);
    Optional<Banca> findByIdAndUsuarioId(Long id, Long userId);
}
