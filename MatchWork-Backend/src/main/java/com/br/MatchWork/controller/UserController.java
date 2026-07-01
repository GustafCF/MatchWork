package com.br.MatchWork.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.MatchWork.entity.dtos.UserRequestDto;
import com.br.MatchWork.entity.dtos.UserResponseDto;
import com.br.MatchWork.service.UserService;

@RestController
@RequestMapping("/us")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> users = service.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        UserResponseDto user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/insert")
    public ResponseEntity<UserResponseDto> insert(@RequestBody UserRequestDto dto) {
        UserResponseDto user = service.createUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}").buildAndExpand(user.name()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        UserResponseDto user = service.update(id, dto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
