package com.br.MatchWork.entity.dtos;

import com.br.MatchWork.entity.Curriculum;

public record UserResponseDto (
    String name,
    Integer age,
    String addres,
    String cpf,
    String email,
    Curriculum curriculum,
    String role
) 
{}
