package com.br.MatchWork.entity.dtos;

public record UserRequestDto(
    String name,
    Integer age,
    String addres,
    String cpf,
    String email
) 
{}
