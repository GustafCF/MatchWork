package com.br.MatchWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.MatchWork.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    
}
