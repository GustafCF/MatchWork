package com.br.MatchWork.service;

import com.br.MatchWork.entity.dtos.LoginRequestDto;
import com.br.MatchWork.entity.dtos.LoginResponseDto;

public interface LoginService {

    LoginResponseDto login(LoginRequestDto dto);
}
