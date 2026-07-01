package com.br.MatchWork.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.br.MatchWork.entity.Login;
import com.br.MatchWork.entity.Role;
import com.br.MatchWork.entity.dtos.LoginRequestDto;
import com.br.MatchWork.entity.dtos.LoginResponseDto;
import com.br.MatchWork.exceptions.ResourceNotFoundException;
import com.br.MatchWork.repository.LoginRepository;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository repository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder encode;

    public LoginServiceImpl(LoginRepository repository,JwtEncoder jwtEncoder, BCryptPasswordEncoder encode) {
        this.repository = repository;
        this.jwtEncoder = jwtEncoder;
        this.encode = encode;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        Login entity = repository.findByEmail(dto.email()).orElseThrow(() -> new ResourceNotFoundException(dto.email()));

        if(!entity.loginValdation(dto, encode)) {
            throw new AccessDeniedException("Email or password invalid!");
        }

        var now = Instant.now();
        var expiresIn = 30000L;
        var scopes = entity.getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
            .issuer("MT-WORK")
            .subject(entity.getId().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .claim("scope", scopes)
            .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponseDto(jwtValue, expiresIn);
    }
}