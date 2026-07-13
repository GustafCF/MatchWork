package com.br.MatchWork.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.MatchWork.entity.Enterprise;
import com.br.MatchWork.entity.Job;
import com.br.MatchWork.entity.Login;
import com.br.MatchWork.entity.Role;
import com.br.MatchWork.entity.User;
import com.br.MatchWork.entity.enums.JobModel;
import com.br.MatchWork.entity.enums.TypeContract;
import com.br.MatchWork.repository.EnterpriseRepository;
import com.br.MatchWork.repository.JobRepository;
import com.br.MatchWork.repository.UserRepository;

@Configuration
public class TestConfig implements CommandLineRunner {

    private final JobRepository jobRepo;
    private final UserRepository userRepo;
    private final EnterpriseRepository enterpriseRepo;
    private final BCryptPasswordEncoder encode;

    public TestConfig(JobRepository jobRepo, UserRepository userRepo, EnterpriseRepository enterpriseRepo, BCryptPasswordEncoder encode) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
        this.enterpriseRepo = enterpriseRepo;
        this.encode = encode;
    }

    @Override
    public void run(String... args) throws Exception {

        Role r1 = new Role("BASIC", "Permissões para Usuários Básicos");
        Role r2 = new Role("ENTERPRISE", "Permissões específicas para Empresas");
        
        User u1 = new User("Verônica", 18, "Brasilia/DF", "1234567", new Login("veve@email.com", encode.encode("12345678")));
        u1.getLogin().getRoles().add(r1);
        userRepo.save(u1);

        Enterprise e1 = new Enterprise("AD-CONTRACT", "Empresa de Tecnologia","Goiânia/GO", "1212121212", "AD AVENIDA 4", new Login("ad@email.com", encode.encode("12345678")));
        e1.getLogin().getRoles().add(r2);
        enterpriseRepo.save(e1);

        Job j1 = new Job("Desenvolvedor JR", "Desenvolvedor Java Junior", "Desenvolver Sistemas", "Formação em TI", "Bônus Extras", JobModel.REMOTE, TypeContract.CLT_PERMANENT, LocalDate.now());
        j1.setEnterprise(e1);
        jobRepo.save(j1);

        e1.getJobs().add(j1);
        enterpriseRepo.save(e1);

    }
}