package com.br.MatchWork.entity.dtos;

public record LoginResponseDto(
    String email,
    String accessToken,
    Long expiresIn
) {

}
