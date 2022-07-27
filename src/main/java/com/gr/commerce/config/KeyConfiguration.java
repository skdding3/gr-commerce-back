package com.gr.commerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyConfiguration {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }
}

