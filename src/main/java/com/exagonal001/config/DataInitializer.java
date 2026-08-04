package com.exagonal001.config;




import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.exagonal001.user.application.service.RolService;
import com.exagonal001.user.application.service.UserService;
import com.exagonal001.user.domain.models.Rol;
import com.exagonal001.user.domain.models.User;

import jakarta.transaction.Transactional;

@Configuration
public class DataInitializer {


    @Bean
    CommandLineRunner initDatabase(UserService userService,RolService rolService, InitializerHelper helper) {
        return args -> {
            helper.initialize(userService, rolService);
        };
    }
}

@Component
class InitializerHelper {

    @Transactional
    public void initialize(UserService userService, RolService rolService) {
        Rol adminRol = new Rol("ADMIN", "Administrador", true);
        if (!rolService.existsByName(adminRol.nombre().getNombre())) {
            adminRol = rolService.createRol(adminRol);
        } else {
            adminRol = rolService.getAllRoles().stream()
                    .filter(rol -> "ADMIN".equals(rol.nombre().getNombre()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No se encontro el rol ADMIN"));
        }
        if (!userService.existsByRol("ADMIN")) {
            userService.createUser(new User(
                    null,
                    "Alex",
                    "Moran",
                    "admin@admin.com",
                    adminRol.id(),
                    "admin"
            ));
        }
    }
        
}

