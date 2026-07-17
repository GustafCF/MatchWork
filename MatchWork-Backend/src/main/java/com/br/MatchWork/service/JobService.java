package com.br.MatchWork.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.MatchWork.entity.dtos.JobAllResponseDto;
import com.br.MatchWork.entity.dtos.JobRequestDto;
import com.br.MatchWork.entity.dtos.JobResponseDto;
import com.br.MatchWork.entity.dtos.StepRequestDto;

public interface JobService {

    Page<JobAllResponseDto> findAll(Pageable pageable);
    JobResponseDto findById(Long id);
    Page<JobAllResponseDto> findByName(String name, Pageable pageable);
    JobResponseDto createJob(String email, JobRequestDto dto);
    JobResponseDto update(Long id, JobRequestDto dto);
    void delete(Long id);
    JobResponseDto createProcessSet(Long id, StepRequestDto dto);
    JobResponseDto candidacies(Long id, String email);
    
}