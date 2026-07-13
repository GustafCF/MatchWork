package com.br.MatchWork.service;

import java.util.List;

import com.br.MatchWork.entity.dtos.JobAllResponseDto;
import com.br.MatchWork.entity.dtos.JobRequestDto;
import com.br.MatchWork.entity.dtos.JobResponseDto;
import com.br.MatchWork.entity.dtos.StepRequestDto;

public interface JobService {

    List<JobAllResponseDto> findAll();
    JobResponseDto findById(Long id);
    JobResponseDto createJob(String email, JobRequestDto dto);
    JobResponseDto update(Long id, JobRequestDto dto);
    void delete(Long id);
    JobResponseDto createProcessSet(Long id, StepRequestDto dto);
    JobResponseDto candidacies(Long id, String email);
    
}