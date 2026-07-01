package com.br.MatchWork.service;

import java.util.List;

import com.br.MatchWork.entity.dtos.JobRequestDto;
import com.br.MatchWork.entity.dtos.JobResponseDto;
import com.br.MatchWork.entity.dtos.StepRequestDto;

public interface JobService {

    List<JobResponseDto> findAll();
    JobResponseDto findById(Long id);
    JobResponseDto createJob(String name, JobRequestDto dto);
    JobResponseDto update(Long id, JobRequestDto dto);
    void delete(Long id);
    JobResponseDto createProcessSet(Long id, StepRequestDto dto);
    
}