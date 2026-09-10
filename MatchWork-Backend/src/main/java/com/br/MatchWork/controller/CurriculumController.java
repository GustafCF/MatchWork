package com.br.MatchWork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.MatchWork.entity.dtos.CurriculumRequestDto;
import com.br.MatchWork.entity.dtos.CurriculumResponseDto;
import com.br.MatchWork.service.CurriculumService;

@RestController
@RequestMapping("/cr")
public class CurriculumController {

    private final CurriculumService service;

    public CurriculumController(CurriculumService service) {
        this.service = service;
    }

    @PostMapping("/insert/{email}")
    public ResponseEntity<CurriculumResponseDto> createCurriculum(@PathVariable String email, @RequestBody CurriculumRequestDto dto) {
        CurriculumResponseDto response = service.createCurriculum(email, dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/remove/{email}")
    public ResponseEntity<CurriculumResponseDto> removeInfo(@PathVariable String email, @RequestBody CurriculumRequestDto dto) {
        CurriculumResponseDto response = service.removeInfo(email, dto);
        return ResponseEntity.ok(response);
    }
}