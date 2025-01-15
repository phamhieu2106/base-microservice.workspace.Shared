package com.henry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig {

    @Bean
    public ExecutorService securityContextExecutorService() {
        return new DelegatingSecurityContextExecutorService(Executors.newCachedThreadPool());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
