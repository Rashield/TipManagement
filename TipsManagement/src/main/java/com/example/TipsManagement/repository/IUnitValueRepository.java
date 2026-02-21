package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.UnitValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface IUnitValueRepository extends JpaRepository<UnitValue, Long> {
//    findTopBy > retorna apenas um registro
//    StartDateLessThan > somente retorna datas menores que a passada
//    OrderByStartDateDesc > ordena pela data de maneira decrescente
    Optional<UnitValue> findTopByStartDateLessThanEqualOrderByStartDateDescIdDesc(LocalDate date);
    boolean existsByStartDate(String name);

}
