package com.br.MatchWork.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.MatchWork.entity.Role;
import com.br.MatchWork.entity.User;
import com.br.MatchWork.entity.dtos.UserRequestDto;
import com.br.MatchWork.entity.dtos.UserResponseDto;
import com.br.MatchWork.entity.mapper.UserMapper;
import com.br.MatchWork.exceptions.ResourceNotFoundException;
import com.br.MatchWork.repository.RoleRepository;
import com.br.MatchWork.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, UserMapper mapper) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = mapper.toEntity(dto);
        Role rl = roleRepo.findById(1L).orElseThrow(()-> new ResourceNotFoundException("1L"));
        user.getLogin().getRoles().add(rl);
        User userSave = userRepo.save(user);
        return mapper.toResponseDto(userSave);
    }

    @Override
    public UserResponseDto findById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toResponseDto(user);
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepo.findAll().stream()
            .map(mapper::toResponseDto)
            .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {
        try {
            User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
            mapper.updateEntityFromDto(user, dto);
            var savedUser = userRepo.save(user);
            return mapper.toResponseDto(savedUser);
        } catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            userRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }
    }
}