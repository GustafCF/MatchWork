package com.br.MatchWork.entity.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.br.MatchWork.entity.Login;
import com.br.MatchWork.entity.User;
import com.br.MatchWork.entity.dtos.UserRequestDto;
import com.br.MatchWork.entity.dtos.UserResponseDto;

@Component
public class UserMapper {

    private final BCryptPasswordEncoder encode;

    public UserMapper(BCryptPasswordEncoder encode) {
        this.encode = encode;
    }

    public User toEntity(UserRequestDto request) {
        return new User(
            request.name(),
            request.age(),
            request.addres(),
            request.cpf(),
            new Login(request.email(), encode.encode(request.password()))
        );
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
            user.getName(),
            user.getAge(),
            user.getAddres(),
            user.getCpf()
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
    }
}