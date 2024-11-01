package com.hellspawn287.basket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    @Bean
    ExecutorService executorService(){
        return Executors.newCachedThreadPool();
    }
}
