package com.br.MatchWork.config;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.br.MatchWork.entity.Curriculum;
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
        
        User u1 = new User("Verônica", 18, "Brasilia/DF", "1234567", new Login("veve@email.com", encode.encode("12345678")), new Curriculum());
        User u2 = new User("Mariana", 24, "Brasilia/DF", "1234567", new Login("may@email.com", encode.encode("12345678")), new Curriculum());
        User u3 = new User("Gustavo", 25, "Brasilia/DF", "1234567", new Login("gu@email.com", encode.encode("12345678")), new Curriculum());
        User u4 = new User("Robert", 30, "Aguas Lindas/GO", "1234567", new Login("rob@email.com", encode.encode("12345678")), new Curriculum());
        User u5 = new User("Spinelli", 42, "Rio de Janeiro/RJ", "1234567", new Login("spi@email.com", encode.encode("12345678")), new Curriculum());
        u1.getLogin().getRoles().add(r1);
        u2.getLogin().getRoles().add(r1);
        u3.getLogin().getRoles().add(r1);
        u4.getLogin().getRoles().add(r1);
        u5.getLogin().getRoles().add(r1);
        userRepo.saveAll(Arrays.asList(u1, u2, u3, u4, u5));

        Enterprise e1 = new Enterprise("AD-CONTRACT", "Empresa de Tecnologia consolidada no mercado de trabalho","Goiânia/GO", "1212121212", "AD AVENIDA 4", new Login("ad@email.com", encode.encode("12345678")));
        Enterprise e2 = new Enterprise("BFF-BEEF", "Restaurante certificado de cinco estrelas", "São Paulo/SP", "1212121212", "AD AVENIDA 4", new Login("bff@email.com", encode.encode("12345678")));
        Enterprise e3 = new Enterprise("DB Automóveis", "Empresa Automobilística que concerta, aluga e vende carros","Rio de Janeiro/RJ", "1212121212", "AD AVENIDA 4", new Login("db@email.com", encode.encode("12345678")));
        Enterprise e4 = new Enterprise("Confeitaria Dos Anjos", "Empresa de Doces","Rio Grande do Sul/RS", "1212121212", "AD AVENIDA 4", new Login("co@email.com", encode.encode("12345678")));
        Enterprise e5 = new Enterprise("Livraria Gama", "ivraria com um amplo aervo de livros, indo dos lançamentos aos mais clássicos","Goiânia/GO", "1212121212", "AD AVENIDA 4", new Login("ga@email.com", encode.encode("12345678")));
        e1.getLogin().getRoles().add(r2);
        e2.getLogin().getRoles().add(r2);
        e3.getLogin().getRoles().add(r2);
        e4.getLogin().getRoles().add(r2);
        e5.getLogin().getRoles().add(r2);
        enterpriseRepo.saveAll(Arrays.asList(e1, e2, e3, e4, e5));

        Job j1 = new Job("Desenvolvedor JR", "Desenvolvedor Java Junior, com foco em Spring-Boot, JPA, Hibernate", "Desenvolver Sistemas utilizando a linguagem de programação Java", "Formação em TI, sem experiência", "Bônus Extras: Vale alimentação, Bonificação por Metas, Vale Transprte", JobModel.REMOTE, TypeContract.CLT_PERMANENT, LocalDate.now().minusDays(4L));
        Job j2 = new Job("Chefe de Cozinha", "Chefe de cozinha no restaurante BFF BEEF", "Preparar Alimentos e Guarnições", "5 anos de êxperiencia como cheffe em restaurantes especialiados em comida vegana", "Bônus Extras: Bonificação por desempenho do Restaurante, VA, VT, Afiliação com PetShop", JobModel.INPERSON, TypeContract.CLT_PERMANENT, LocalDate.now());
        Job j3 = new Job("Graçom", "Garçom experiênte", "Servir com delicadeza e simpatia", "Experiência servindo e limpando mesas, e abrindo e servindo vinhos", "Bônus Extras: VA, VL, Goreta de 10%", JobModel.INPERSON, TypeContract.CLT_PERMANENT, LocalDate.now().minusMonths(1L));
        Job j4 = new Job("Mecânico", "Mecânico de carros e motos antigas", "Desmontar, consertar e Montar carros antigos", "Conhecimento e experiêcia em reforma de carros antigos", "Bônus Extras: VA ou VR, VL, Boificação por Metas", JobModel.INPERSON, TypeContract.CLT_PERMANENT, LocalDate.now());
        j1.setEnterprise(e1);
        j2.setEnterprise(e2);
        j3.setEnterprise(e2);
        j4.setEnterprise(e3);
        jobRepo.saveAll(Arrays.asList(j1, j2, j3, j4));

        e1.getJobs().add(j1);
        e2.getJobs().add(j2);
        e2.getJobs().add(j3);
        e3.getJobs().add(j4);
        enterpriseRepo.saveAll(Arrays.asList(e1, e2, e3));
    }
}