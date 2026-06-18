package com.br.MatchWork.service;

import java.util.List;

import com.br.MatchWork.entity.dtos.UserRequestDto;
import com.br.MatchWork.entity.dtos.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserRequestDto dto);
    UserResponseDto findById(Long id);
    List<UserResponseDto> findAll();
    UserResponseDto update(Long id, UserRequestDto dto);
    void delete(Long id);

}