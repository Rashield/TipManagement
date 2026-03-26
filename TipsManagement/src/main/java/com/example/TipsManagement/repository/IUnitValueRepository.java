package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.UnitValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUnitValueRepository extends JpaRepository<UnitValue, Long> {
    Optional<UnitValue> findTopByUsuarioIdOrderByIdDesc(Long userId);
    List<UnitValue> findAllByUsuarioId(Long userId);

}
