package com.br.MatchWork.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.MatchWork.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    
    @Query("SELECT j FROM Job j WHERE LOWER(j.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Job> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
