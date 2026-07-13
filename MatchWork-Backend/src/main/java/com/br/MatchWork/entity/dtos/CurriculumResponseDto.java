package com.br.MatchWork.entity.dtos;

import java.util.List;

public record CurriculumResponseDto(
    List<String> education,
    List<String> experience,
    List<String> skills
) {}