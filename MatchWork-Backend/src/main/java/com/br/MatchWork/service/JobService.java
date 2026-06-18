package com.br.MatchWork.service;

import java.util.List;

import com.br.MatchWork.entity.dtos.JobRequestDto;
import com.br.MatchWork.entity.dtos.JobResponseDto;

public interface JobService {

    List<JobResponseDto> findAll();
    JobResponseDto findById(Long id);
    JobResponseDto createJob(JobRequestDto dto);
    JobResponseDto update(Long id, JobRequestDto dto);
    void delete(Long id);
    
}