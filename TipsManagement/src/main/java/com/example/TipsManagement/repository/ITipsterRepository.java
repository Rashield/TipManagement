package com.example.TipsManagement.repository;

import com.example.TipsManagement.model.Tipster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipsterRepository extends JpaRepository<Tipster, Long> {

}
