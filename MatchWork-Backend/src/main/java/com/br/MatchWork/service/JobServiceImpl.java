package com.br.MatchWork.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.MatchWork.entity.Enterprise;
import com.br.MatchWork.entity.Job;
import com.br.MatchWork.entity.ProcessSteps;
import com.br.MatchWork.entity.dtos.JobRequestDto;
import com.br.MatchWork.entity.dtos.JobResponseDto;
import com.br.MatchWork.entity.dtos.StepRequestDto;
import com.br.MatchWork.entity.mapper.JobMapper;
import com.br.MatchWork.exceptions.ResourceNotFoundException;
import com.br.MatchWork.repository.EnterpriseRepository;
import com.br.MatchWork.repository.JobRepository;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepo;
    private final EnterpriseRepository enterpriseRepo;
    private final JobMapper mapper;

    public JobServiceImpl(JobRepository jobRepo, EnterpriseRepository enterpriseRepo, JobMapper mapper) {
        this.jobRepo = jobRepo;
        this.enterpriseRepo = enterpriseRepo;
        this.mapper = mapper;
    }

    @Override
    public List<JobResponseDto> findAll() {
        return jobRepo.findAll().stream()
            .map(mapper::toResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public JobResponseDto findById(Long id) {
        Job job = jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toResponseDto(job);
    }

    @Override
    public JobResponseDto createJob(String name, JobRequestDto dto) {
        Enterprise enterprise = enterpriseRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException(name));
        Job job = mapper.toEntity(dto);
        job.setEnterprise(enterprise);
        Job savedJob = jobRepo.save(job);
        return mapper.toResponseDto(savedJob);
    }

    @Override
    public JobResponseDto update(Long id, JobRequestDto dto) {
        Job job = jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        mapper.updateEntityFromDto(job, dto);
        Job savedJob = jobRepo.save(job);
        return mapper.toResponseDto(savedJob);
    }

    @Override
    public void delete(Long id) {
        jobRepo.deleteById(id);
    }

    @Override
    public JobResponseDto createProcessSet(Long id, StepRequestDto dto) {
        Job job = jobRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        job.getSteps().add(new ProcessSteps(dto.number(), dto.name(), dto.description()));
        Job savedJob = jobRepo.save(job);
        return mapper.toResponseDto(savedJob);
    }
}