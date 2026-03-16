package com.tramplin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.tramplin.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class TramplinApplication {

    public static void main(String[] args) {
        SpringApplication.run(TramplinApplication.class, args);
    }
}