package com.br.MatchWork.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.MatchWork.entity.Enterprise;
import com.br.MatchWork.entity.Role;
import com.br.MatchWork.entity.dtos.EnterpriseRequestDto;
import com.br.MatchWork.entity.dtos.EnterpriseResponseDto;
import com.br.MatchWork.entity.mapper.EnterpriseMapper;
import com.br.MatchWork.exceptions.ResourceNotFoundException;
import com.br.MatchWork.repository.EnterpriseRepository;
import com.br.MatchWork.repository.RoleRepository;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository repository;
    private final RoleRepository roleRepo;
    private final EnterpriseMapper mapper;

    public EnterpriseServiceImpl(EnterpriseRepository repository, RoleRepository roleRepo, EnterpriseMapper mapper) {
        this.repository = repository;
        this.roleRepo = roleRepo;
        this.mapper = mapper;
    }

    @Override
    public List<EnterpriseResponseDto> findAll() {
        return repository.findAll().stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public EnterpriseResponseDto findById(Long id) {
        Enterprise entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toResponse(entity);
    }

    @Override
    public EnterpriseResponseDto createEnterprise(EnterpriseRequestDto dto) {
        Role role = roleRepo.findByName("ENTERPRISE").orElseThrow(()-> new ResourceNotFoundException("ENTERPRISE"));
        Enterprise entity = mapper.toEntity(dto);
        entity.getLogin().getRoles().add(role);
        Enterprise savedEntity = repository.save(entity);
        return mapper.toResponse(savedEntity);
    }

    @Override
    public EnterpriseResponseDto update(Long id, EnterpriseRequestDto dto) {
        Enterprise entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        mapper.update(entity, dto);
        Enterprise savedEntity = repository.save(entity);
        return mapper.toResponse(savedEntity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}