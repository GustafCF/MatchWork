package com.br.MatchWork.entity.mapper;

import org.springframework.stereotype.Component;

import com.br.MatchWork.entity.User;
import com.br.MatchWork.entity.dtos.UserRequestDto;
import com.br.MatchWork.entity.dtos.UserResponseDto;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto request) {
        return new User(
            request.name(),
            request.age(),
            request.addres(),
            request.cpf(),
            request.email()
        );
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
            user.getName(),
            user.getAge(),
            user.getAddres(),
            user.getCpf(),
            user.getEmail()
        );
    }

    public void updateEntityFromDto(User user, UserRequestDto dto) {
        if (!dto.name().isBlank()) {
            user.setName(dto.name());
        }
        if(dto.age() != null) {
            user.setAge(dto.age());
        }
        if(!dto.addres().isBlank()) {
            user.setAddres(dto.addres());
        }
        if(!dto.cpf().isBlank()){
            user.setCpf(dto.cpf());
        }
        if(!dto.email().isBlank()){
            user.setEmail(dto.email());
        }
    }
}