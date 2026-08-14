package com.br.MatchWork.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.br.MatchWork.entity.Login;
import com.br.MatchWork.entity.Role;
import com.br.MatchWork.entity.User;
import com.br.MatchWork.entity.dtos.UserRequestDto;
import com.br.MatchWork.entity.dtos.UserResponseDto;
import com.br.MatchWork.entity.mapper.UserMapper;
import com.br.MatchWork.exceptions.ResourceNotFoundException;
import com.br.MatchWork.repository.RoleRepository;
import com.br.MatchWork.repository.UserRepository;
import com.br.MatchWork.service.UserServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private RoleRepository roleRepo;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl service;

    private User user;
    private UserRequestDto userRequest;
    private UserResponseDto userResponse;
    private Role role;

    @BeforeEach
    void setup() {

        role = new Role("BASIC", "Permissões para Usuários Básicos");
        role.setId(1L);

        userRequest = new UserRequestDto(
            "João Silva",
            30,
            "Rua A, 123",
            "12345678901",
            "joao@email.com",
            "senha123"
        );

        Login login = new Login("joao@email.com", "senhaCriptografada");
        user = new User("João Silva", 30, "Rua A, 123", "12345678901", login);
        user.setId(1L);

        userResponse = new UserResponseDto(
            "João Silva",
            30,
            "Rua A, 123",
            "12345678901",
            "joao@email.com",
            null,
            ""
        );
    }

    @Test
    @DisplayName("Success Create User")
    void createUser_Success() {
        when(roleRepo.findByName("BASIC")).thenReturn(Optional.of(role));
        when(mapper.toEntity(userRequest)).thenReturn(user);
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(mapper.toResponseDto(user)).thenReturn(userResponse);

        UserResponseDto result = service.createUser(userRequest);

        assertEquals(userResponse, result);
        verify(roleRepo).findByName("BASIC");
        verify(mapper).toEntity(userRequest);
        verify(userRepo).save(user);
        verify(mapper).toResponseDto(user);
    }

    @Test
    @DisplayName("Role Not Found")
    void createUserRoleNotFound() {
        when(roleRepo.findByName("BASIC")).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.createUser(userRequest)
        );
        assertEquals("Resource not found: BASIC", exception.getMessage());
        verify(roleRepo).findByName("BASIC");
        verifyNoInteractions(mapper, userRepo);
    }

    @Test
    @DisplayName("Find User by ID - Success")
    void findById_Success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toResponseDto(user)).thenReturn(userResponse);

        UserResponseDto result = service.findById(1L);

        assertEquals(userResponse, result);
        verify(userRepo).findById(1L);
        verify(mapper).toResponseDto(user);
    }

    @Test
    @DisplayName("Find User by ID - Not Found")
    void findById_NotFound() {
        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.findById(99L)
        );
        assertEquals("Resource not found: 99", exception.getMessage());
        verify(userRepo).findById(99L);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Find All Users - Success")
    void findAll_Success() {
        User user2 = new User("Maria", 25, "Rua B", "98765432100", new Login("maria@email.com", "senha"));
        user2.setId(2L);
        UserResponseDto response2 = new UserResponseDto("Maria", 25, "Rua B", "98765432100", "maria@email.com", null, "");

        when(userRepo.findAll()).thenReturn(List.of(user, user2));
        when(mapper.toResponseDto(user)).thenReturn(userResponse);
        when(mapper.toResponseDto(user2)).thenReturn(response2);

        List<UserResponseDto> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals(userResponse, result.get(0));
        assertEquals(response2, result.get(1));
        verify(userRepo).findAll();
        verify(mapper).toResponseDto(user);
        verify(mapper).toResponseDto(user2);
    }

    @Test
    @DisplayName("Find All Users - Empty List")
    void findAll_Empty() {
        when(userRepo.findAll()).thenReturn(List.of());
        List<UserResponseDto> result = service.findAll();
        assertEquals(0, result.size());
        verify(userRepo).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Update User - Success")
    void update_Success() {
        UserRequestDto updateDto = new UserRequestDto(
            "João Atualizado",
            31,
            "Rua C, 456",
            "11122233344",
            "joao@email.com",
            "novaSenha"
        );

        User updatedUser = new User("João Atualizado", 31, "Rua C, 456", "11122233344", user.getLogin());
        updatedUser.setId(1L);
        UserResponseDto updatedResponse = new UserResponseDto(
            "João Atualizado",
            31,
            "Rua C, 456",
            "11122233344",
            "joao@email.com",
            null,
            ""
        );

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(updatedUser);
        when(mapper.toResponseDto(updatedUser)).thenReturn(updatedResponse);

        UserResponseDto result = service.update(1L, updateDto);

        assertEquals(updatedResponse, result);
        verify(userRepo).findById(1L);
        verify(mapper).updateEntityFromDto(user, updateDto);
        verify(userRepo).save(user);
        verify(mapper).toResponseDto(updatedUser);
    }

    @Test
    @DisplayName("Update User - Not Found")
    void update_NotFound() {
        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.update(99L, userRequest)
        );
        assertEquals("Resource not found: 99", exception.getMessage());
        verify(userRepo).findById(99L);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Update User - Repository throws EntityNotFoundException")
    void update_EntityNotFoundException() {
        when(userRepo.findById(1L)).thenThrow(EntityNotFoundException.class);

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.update(1L, userRequest)
        );
        assertEquals("Resource not found: 1", exception.getMessage());
        verify(userRepo).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Delete User - Success")
    void delete_Success() {
        service.delete(1L);
        verify(userRepo).deleteById(1L);
    }

    @Test
    @DisplayName("Delete User - Not Found (EmptyResultDataAccessException)")
    void delete_NotFound() {
        doThrow(new EmptyResultDataAccessException(1)).when(userRepo).deleteById(99L);

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> service.delete(99L)
        );
        assertEquals("Resource not found: 99", exception.getMessage());
        verify(userRepo).deleteById(99L);
    }
}