package com.br.MatchWork.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Page<JobAllResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<JobAllResponseDto> jobs = service.findAll(pageable);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<JobResponseDto> findById(@PathVariable Long id) {
        JobResponseDto job = service.findById(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<JobAllResponseDto>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findByName(name, pageable));
    }

    @PostMapping("/insert/{email}")
    @PreAuthorize("hasRole('ENTERPRISE')")
    public ResponseEntity<JobResponseDto> insert(@PathVariable String email, @RequestBody JobRequestDto dto) {
        JobResponseDto job = service.createJob(email, dto);
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