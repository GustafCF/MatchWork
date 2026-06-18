package com.br.MatchWork.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.MatchWork.entity.dtos.EnterpriseRequestDto;
import com.br.MatchWork.entity.dtos.EnterpriseResponseDto;
import com.br.MatchWork.service.EnterpriseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/en")
public class EnterpriseController {

    private final EnterpriseService service;
    
    public EnterpriseController(EnterpriseService service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<EnterpriseResponseDto>> findAll() {
        List<EnterpriseResponseDto> entities = service.findAll();
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<EnterpriseResponseDto> findById(@PathVariable Long id) {
        EnterpriseResponseDto entity = service.findById(id);
        return ResponseEntity.ok(entity);
    }

    @PostMapping("/insert")
    public ResponseEntity<EnterpriseResponseDto> insert(@RequestBody EnterpriseRequestDto dto) {
        EnterpriseResponseDto entity = service.createEnterprise(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}").buildAndExpand(dto.name()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }
}
