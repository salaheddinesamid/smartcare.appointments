package com.healthcare.appointment_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Configurations {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
