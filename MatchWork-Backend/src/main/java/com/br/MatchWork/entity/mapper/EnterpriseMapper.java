package com.br.MatchWork.entity.mapper;

import org.springframework.stereotype.Component;

import com.br.MatchWork.entity.Enterprise;
import com.br.MatchWork.entity.dtos.EnterpriseRequestDto;
import com.br.MatchWork.entity.dtos.EnterpriseResponseDto;

@Component
public class EnterpriseMapper {
    
    public Enterprise toEntity(EnterpriseRequestDto request) {
        return new Enterprise(
            request.name(),
            request.description(),
            request.location(),
            request.cnpj(),
            request.poBox()
        );
    }

    public EnterpriseResponseDto toResponse(Enterprise enterprise) {
        return new EnterpriseResponseDto(
            enterprise.getName(),
            enterprise.getDescription(),
            enterprise.getLocation(),
            enterprise.getCnpj(),
            enterprise.getPoBox()
        );
    }

    public void update(Enterprise entity, EnterpriseRequestDto dto) {
        if(!dto.name().isBlank()) {
            entity.setName(dto.name());
        }
        if(!dto.description().isBlank()) {
            entity.setDescription(dto.description());
        }
        if(!dto.location().isBlank()) {
            entity.setLocation(dto.location());
        }
        if(!dto.cnpj().isBlank()) {
            entity.setCnpj(dto.cnpj());
        }
        if(!dto.poBox().isBlank()) {
            entity.setPoBox(dto.poBox());
        }
    }

}