package com.exagonal001.config;




import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exagonal001.user.application.service.UserService;
import com.exagonal001.user.domain.models.User;

@Configuration
public class DataInitializer {

    

    @Bean
    CommandLineRunner initDatabase(UserService userService, InitializerHelper helper) {
        return args -> {
            helper.initialize(userService);
        };
    }
}

@org.springframework.stereotype.Component
class InitializerHelper {

    @org.springframework.transaction.annotation.Transactional
    public void initialize(UserService userService) {
        if (!userService.existsByRol("ADMIN")) {
            userService.createUser(new User(
                    null,
                    "Alex",
                    "Moran",
                    "ADMIN",
                    "admin"
            ));
        }
    }
        
}

