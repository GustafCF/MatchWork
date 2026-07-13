package com.br.MatchWork.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.br.MatchWork.controller.UserController;
import com.br.MatchWork.entity.dtos.UserRequestDto;
import com.br.MatchWork.entity.dtos.UserResponseDto;
import com.br.MatchWork.service.SecurityService;
import com.br.MatchWork.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean(name = "securityService")
    private SecurityService securityService;

    private UserRequestDto validRequestDto;
    private UserResponseDto validResponseDto;

    @EnableMethodSecurity
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
                http
                        .csrf(AbstractHttpConfigurer::disable)
                        .anonymous(AbstractHttpConfigurer::disable) 
                        .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/us/insert").permitAll()
                        .anyRequest().authenticated()
                        )
                        .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                        );
                return http.build();
        }
    }

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {

        validRequestDto = new UserRequestDto(
                "João Silva",
                25,
                "Rua A, 123",
                "123.456.789-00",
                "joao@email.com",
                "senha123"
        );

        validResponseDto = new UserResponseDto(
                "João Silva",
                25,
                "Rua A, 123",
                "123.456.789-00",
                "joao@email.com",
                null 
        );
    }

    @Test
    @WithMockUser(roles = "BASIC")
    void findAll_shouldReturnListOfUsers() throws Exception {
        when(userService.findAll()).thenReturn(List.of(validResponseDto));

        mockMvc.perform(get("/us/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("João Silva"))
                .andExpect(jsonPath("$[0].email").value("joao@email.com"));
    }

    @Test
    void findAll_withoutAuthentication_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/us/findAll"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "BASIC")
    void findById_whenUserIsOwner_shouldReturnUser() throws Exception {
        Long userId = 1L;
        when(securityService.isOwnerUser(userId)).thenReturn(true);
        when(userService.findById(userId)).thenReturn(validResponseDto);

        mockMvc.perform(get("/us/find/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva"));
    }

    @Test
    @WithMockUser(roles = "BASIC")
    void findById_whenUserIsNotOwner_shouldReturnForbidden() throws Exception {
        Long userId = 1L;
        when(securityService.isOwnerUser(userId)).thenReturn(false);

        mockMvc.perform(get("/us/find/{id}", userId))
                .andExpect(status().isForbidden());
    }

    @Test
    void findById_withoutAuthentication_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/us/find/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void insert_withValidData_shouldReturnCreated() throws Exception {
        when(userService.createUser(any(UserRequestDto.class))).thenReturn(validResponseDto);

        mockMvc.perform(post("/us/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("João Silva"));
    }

    @Test
    void insert_withBlankEmail_shouldReturnBadRequest() throws Exception {
        UserRequestDto invalidDto = new UserRequestDto(
                "João", 25, "Rua", "123", "", "senha"
        );

        mockMvc.perform(post("/us/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void insert_withBlankPassword_shouldReturnBadRequest() throws Exception {
        UserRequestDto invalidDto = new UserRequestDto(
                "João", 25, "Rua", "123", "joao@email.com", ""
        );

        mockMvc.perform(post("/us/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "BASIC")
    void update_whenUserIsOwner_shouldReturnUpdatedUser() throws Exception {
        Long userId = 1L;
        when(securityService.isOwnerUser(userId)).thenReturn(true);
        when(userService.update(eq(userId), any(UserRequestDto.class))).thenReturn(validResponseDto);

        mockMvc.perform(put("/us/update/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva"));
    }

    @Test
    @WithMockUser(roles = "BASIC")
    void update_whenUserIsNotOwner_shouldReturnForbidden() throws Exception {
        Long userId = 1L;
        when(securityService.isOwnerUser(userId)).thenReturn(false);

        mockMvc.perform(put("/us/update/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void update_withoutAuthentication_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(put("/us/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "BASIC")
    void delete_whenUserIsOwner_shouldReturnNoContent() throws Exception {
        Long userId = 1L;
        when(securityService.isOwnerUser(userId)).thenReturn(true);
        doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/us/delete/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "BASIC")
    void delete_whenUserIsNotOwner_shouldReturnForbidden() throws Exception {
        Long userId = 1L;
        when(securityService.isOwnerUser(userId)).thenReturn(false);

        mockMvc.perform(delete("/us/delete/{id}", userId))
                .andExpect(status().isForbidden());
    }

    @Test
    void delete_withoutAuthentication_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(delete("/us/delete/1"))
                .andExpect(status().isUnauthorized());
    }
}