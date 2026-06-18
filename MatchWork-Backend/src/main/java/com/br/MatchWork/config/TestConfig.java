package com.br.MatchWork.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.br.MatchWork.entity.User;
import com.br.MatchWork.repository.UserRepository;

@Configuration
public class TestConfig implements CommandLineRunner {

    private UserRepository userRepo;

    public TestConfig(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        
        User u1 = new User("Verônica", 18, "Brasilia/DF", "1234567", "veve@email.com");
        User u2 = new User("AD-CONTRACT", null, "Goiânia/GO", "1234567", "ad@email.com");
        userRepo.saveAll(Arrays.asList(u1, u2));

    }
}