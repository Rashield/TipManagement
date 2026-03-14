package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.Tipster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITipsterRepository extends JpaRepository<Tipster, Long> {
    boolean existsByName(String name);
    boolean existsById(Long id);
    Optional<Tipster> findByName(String name);
}
