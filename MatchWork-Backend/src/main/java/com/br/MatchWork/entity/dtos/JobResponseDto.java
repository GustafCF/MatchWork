package com.br.MatchWork.entity.dtos;

import java.time.LocalDate;
import java.util.Set;

import com.br.MatchWork.entity.Enterprise;
import com.br.MatchWork.entity.ProcessSteps;
import com.br.MatchWork.entity.enums.JobModel;
import com.br.MatchWork.entity.enums.TypeContract;

public record JobResponseDto(
    Long id,
    String name,
    String description,
    String responsibility,
    String requirements,
    String additionalInfo,
    Set<ProcessSteps> steps,
    JobModel jobModel,
    TypeContract typeContract,
    Enterprise enterprise,
    LocalDate date
) 
{}
