package com.br.MatchWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.MatchWork.entity.ProcessSteps;

@Repository
public interface ProcessStepsRepository extends JpaRepository<ProcessSteps, Long> {

}