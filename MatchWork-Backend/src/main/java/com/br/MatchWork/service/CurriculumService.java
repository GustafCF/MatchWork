package com.br.MatchWork.service;

import com.br.MatchWork.entity.dtos.CurriculumRequestDto;
import com.br.MatchWork.entity.dtos.CurriculumResponseDto;

public interface CurriculumService {

    CurriculumResponseDto createCurriculum(String email, CurriculumRequestDto dto);

}
