package com.br.MatchWork.entity.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
    @NotBlank
    String email,
    @NotBlank
    String password
) {

}
