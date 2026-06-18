package com.br.MatchWork.entity.dtos;

public record EnterpriseResponseDto (
    String name,
    String description,
    String location,
    String cnpj,
    String poBox
) {}
