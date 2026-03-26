package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.Banca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBancaRepository extends JpaRepository<Banca, Long> {

}
