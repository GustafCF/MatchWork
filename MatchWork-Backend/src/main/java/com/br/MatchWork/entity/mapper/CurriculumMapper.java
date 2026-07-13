package com.br.MatchWork.entity.mapper;

import org.springframework.stereotype.Component;

import com.br.MatchWork.entity.Curriculum;
import com.br.MatchWork.entity.dtos.CurriculumRequestDto;
import com.br.MatchWork.entity.dtos.CurriculumResponseDto;

@Component
public class CurriculumMapper {

    public CurriculumResponseDto toResponse(Curriculum obj) {
        return new CurriculumResponseDto(
            obj.getEducation(),
            obj.getExperience(),
            obj.getSkills()
        );
    }

    public void create(Curriculum entity, CurriculumRequestDto dto) {
        if (!dto.education().isBlank()) {
            entity.getEducation().add(dto.education());
        }
        if(!dto.experience().isBlank()) {
            entity.getExperience().add(dto.experience());
        }
        if(!dto.skills().isBlank()) {
            entity.getSkills().add(dto.skills());
        }
    }

}
