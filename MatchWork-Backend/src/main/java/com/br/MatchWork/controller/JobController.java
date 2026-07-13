package com.br.MatchWork.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.MatchWork.entity.dtos.JobAllResponseDto;
import com.br.MatchWork.entity.dtos.JobRequestDto;
import com.br.MatchWork.entity.dtos.JobResponseDto;
import com.br.MatchWork.service.JobServiceImpl;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobServiceImpl service;

    public JobController(JobServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<JobAllResponseDto>> findAll() {
        List<JobAllResponseDto> jobs = service.findAll();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<JobResponseDto> findById(@PathVariable Long id) {
        JobResponseDto job = service.findById(id);
        return ResponseEntity.ok(job);
    }

    @PostMapping("/insert/{name}")
    @PreAuthorize("hasRole('ENTERPRISE')")
    public ResponseEntity<JobResponseDto> insert(@PathVariable String name, @RequestBody JobRequestDto dto) {
        JobResponseDto job = service.createJob(name, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}").buildAndExpand(job.name()).toUri();
        return ResponseEntity.created(uri).body(job);
    }

    @PostMapping("/candidate/{id}/{email}")
    public ResponseEntity<JobResponseDto> candidacies(@PathVariable Long id, @PathVariable String email) {
        JobResponseDto job = service.candidacies(id, email);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ENTERPRISE') and @securityService.isOwnerEnterprise(#id)")
    public ResponseEntity<JobResponseDto> update(@PathVariable Long id, @RequestBody JobRequestDto dto) {
        JobResponseDto job = service.update(id, dto);
        return ResponseEntity.ok(job);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ENTERPRISE') and @securityService.isOwnerEnterprise(#id)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}