package com.br.MatchWork.entity.dtos;

public record EnterpriseRequestDto(
    String name,
    String description,
    String location,
    String cnpj,
    String poBox,
    String email,
    String password
) {}