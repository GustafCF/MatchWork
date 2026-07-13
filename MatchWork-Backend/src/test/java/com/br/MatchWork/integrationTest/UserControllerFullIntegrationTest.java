package com.br.MatchWork.integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.br.MatchWork.entity.User;
import com.br.MatchWork.entity.dtos.UserRequestDto;
import com.br.MatchWork.repository.UserRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional 
public class UserControllerFullIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private UserRequestDto validRequestDto;

    @BeforeEach
    void setUp() {
        validRequestDto = new UserRequestDto(
                "João Silva",
                25,
                "Rua A, 123",
                "123.456.789-00",
                "joao@email.com",
                "senha123"
        );
    }

    @Test
    void createUser_shouldPersistInDatabaseAndReturnCreated() throws Exception {
        mockMvc.perform(post("/us/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        User savedUser = userRepository.findByLogin_Email("joao@email.com").orElse(null);
        assertNotNull(savedUser);
        assertEquals("João Silva", savedUser.getName());
    }
}