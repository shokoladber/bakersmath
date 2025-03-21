package com.michaelrkaplan.bakersmath.config;

import com.michaelrkaplan.bakersmath.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializationConfig {

    // Your other configurations...

    @Bean
    public CommandLineRunner initializeRoles(RoleService roleService) {
        return args -> {
            roleService.initializeRoles();
        };
    }
}
