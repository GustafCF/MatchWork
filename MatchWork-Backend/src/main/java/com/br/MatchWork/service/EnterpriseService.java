package com.br.MatchWork.service;

import java.util.List;

import com.br.MatchWork.entity.dtos.EnterpriseRequestDto;
import com.br.MatchWork.entity.dtos.EnterpriseResponseDto;

public interface EnterpriseService {

    List<EnterpriseResponseDto> findAll();
    EnterpriseResponseDto findById(Long id); 
    EnterpriseResponseDto createEnterprise(EnterpriseRequestDto dto);
    EnterpriseResponseDto update(Long id, EnterpriseRequestDto dto);
    void delete(Long id); 

}