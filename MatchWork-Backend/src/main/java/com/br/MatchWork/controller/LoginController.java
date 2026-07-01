package com.br.MatchWork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.MatchWork.entity.dtos.LoginRequestDto;
import com.br.MatchWork.entity.dtos.LoginResponseDto;
import com.br.MatchWork.service.LoginServiceImpl;

@RestController
@RequestMapping("/lg")
public class LoginController {

    private final LoginServiceImpl service;

    public LoginController(LoginServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        var response = service.login(dto);
        return ResponseEntity.ok(response);
    }
}
