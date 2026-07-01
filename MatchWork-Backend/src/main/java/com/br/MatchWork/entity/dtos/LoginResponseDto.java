package com.br.MatchWork.entity.dtos;

public record LoginResponseDto(
    String accessToken,
    Long expiresIn
) {

}
