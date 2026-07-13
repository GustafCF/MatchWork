package com.br.MatchWork.entity.dtos;

import jakarta.validation.constraints.NotBlank;

public record EnterpriseRequestDto(
    String name,
    String description,
    String location,
    String cnpj,
    String poBox,
    @NotBlank
    String email,
    @NotBlank
    String password
) {}