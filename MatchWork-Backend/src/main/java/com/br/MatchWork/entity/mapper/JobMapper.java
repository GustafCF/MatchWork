package com.br.MatchWork.entity.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.br.MatchWork.entity.Job;
import com.br.MatchWork.entity.dtos.JobRequestDto;
import com.br.MatchWork.entity.dtos.JobResponseDto;

@Component
public class JobMapper {

    public Job toEntity(JobRequestDto request) {
        return new Job(
            request.name(),
            request.description(),
            request.responsibility(),
            request.requirements(),
            request.additionalInfo(),
            request.jobModel(),
            request.typeContract(),
            LocalDate.now()
        );
    }

    public JobResponseDto toResponseDto(Job job) {
        return new JobResponseDto(
            job.getName(),
            job.getDescription(),
            job.getResponsibility(),
            job.getRequirements(),
            job.getAdditionalInfo(),
            job.getSteps(),
            job.getJobModel(),
            job.getTypeContract(),
            job.getDate()
        );
    }

    public void updateEntityFromDto(Job job, JobRequestDto dto) {
        if(!dto.name().isBlank()) {
            job.setName(dto.name());
        }
        if(!dto.description().isBlank()) {
            job.setDescription(dto.description());
        }
        if(!dto.responsibility().isBlank()) {
            job.setResponsibility(dto.responsibility());
        }
        if(!dto.requirements().isBlank()) {
            job.setRequirements(dto.requirements());
        }
        if(!dto.additionalInfo().isBlank()) {
            job.setAdditionalInfo(dto.additionalInfo());
        }
        if(dto.jobModel() != null) {
            job.setJobModel(dto.jobModel());
        }
        if(dto.typeContract() != null) {
            job.setTypeContract(dto.typeContract());
        }
    }
}