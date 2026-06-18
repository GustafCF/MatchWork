package com.br.MatchWork.entity.dtos;

import java.time.LocalDate;

import com.br.MatchWork.entity.enums.JobModel;
import com.br.MatchWork.entity.enums.TypeContract;

public record JobRequestDto(
    String name,
    String description,
    String responsibility,
    String requirements,
    String additionalInfo,
    JobModel jobModel,
    TypeContract typeContract,
    LocalDate date
) 
{}