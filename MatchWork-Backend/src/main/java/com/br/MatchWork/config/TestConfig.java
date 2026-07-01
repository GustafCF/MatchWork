package com.br.MatchWork.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.MatchWork.entity.Enterprise;
import com.br.MatchWork.entity.Login;
import com.br.MatchWork.entity.Role;
import com.br.MatchWork.entity.User;
import com.br.MatchWork.repository.EnterpriseRepository;
import com.br.MatchWork.repository.RoleRepository;
import com.br.MatchWork.repository.UserRepository;

@Configuration
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepo;
    private final EnterpriseRepository enterpriseRepo;
    private final RoleRepository roleRepo;
    private final BCryptPasswordEncoder encode;

    public TestConfig(UserRepository userRepo, EnterpriseRepository enterpriseRepo, RoleRepository roleRepo, BCryptPasswordEncoder encode) {
        this.userRepo = userRepo;
        this.enterpriseRepo = enterpriseRepo;
        this.roleRepo = roleRepo;
        this.encode = encode;
    }

    @Override
    public void run(String... args) throws Exception {

        Role r1 = new Role("Base", "Base");
        
        User u1 = new User("Verônica", 18, "Brasilia/DF", "1234567", new Login("veve@email.com", encode.encode("12345678")));
        // u1.getLogin().getRoles().add(r1);
        userRepo.save(u1);

        Enterprise e1 = new Enterprise("AD-CONTRACT", "Empresa de Tecnologia","Goiânia/GO", "1212121212", "AD AVENIDA 4", new Login("ad@email.com", encode.encode("12345678")));
        e1.getLogin().getRoles().add(r1);
        enterpriseRepo.save(e1);

    }
}