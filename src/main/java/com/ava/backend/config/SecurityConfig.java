package com.ava.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Argon2PasswordEncoder parameters:
        // saltLength: 16 bytes
        // hashLength: 32 bytes
        // parallelism: 1 thread
        // memory: 16384 KB (16 MB)
        // iterations: 2
        return new Argon2PasswordEncoder(16, 32, 1, 16384, 2);
    }
}
