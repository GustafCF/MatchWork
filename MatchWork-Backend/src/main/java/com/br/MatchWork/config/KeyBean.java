package com.br.MatchWork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.br.MatchWork.rsa.RsaKeyGenerator;

@Configuration
public class KeyBean {

    @Bean
    public RsaKeyGenerator rsaKeyGenerator() {
        return new RsaKeyGenerator();
    }

}